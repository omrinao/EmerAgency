package Models;


import DBAdapters.DBEvent;

import java.sql.*;
import java.util.*;

public class Model {
    //Const
    private final String DB_URL = "jdbc:sqlite:resources/db.db";
    private final String TRNS_BEGIN = "BEGIN TRANSACTION;";
    private final String COMMIT = "COMMIT;";


    // helpful attributes
    private ResultSet m_results;

    public Model() {

    }

    /**
     * a method to return a connection with the Database
     *
     * @return - a connection if success, null otherwise
     */
    public Connection make_connection() {

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }


    public boolean addEvent(Event event) {
        int affectedRows = -1;
        int id = -1;
        DBEvent dbEvent = new DBEvent(event);
        String sEvent = dbEvent.insertEvents();

        try (Connection connection = make_connection();
             PreparedStatement insertEvent = connection.prepareStatement(sEvent);) {
            connection.setAutoCommit(false);

            //insert event
            affectedRows = insertEvent.executeUpdate();
            if (affectedRows != 1){
                connection.rollback();
            }else {
                id = insertEvent.getGeneratedKeys().getInt(1);
                event.set_id(id);
            }

            //insert update
            Statement cur = connection.createStatement();
            affectedRows = cur.executeUpdate(dbEvent.insertUpdate());
            if (affectedRows!=1){
                connection.rollback();
                throw new SQLException("Failed to insert update. Rolledback");
            }else {
                id = cur.getGeneratedKeys().getInt(1);
                event.get_updates().get(0).set_id(id);
            }

            // insert categories
            cur = connection.createStatement();
            for (String sc : dbEvent.insertEventsCategories())
                cur.addBatch(sc);
            affectedRows = cur.executeBatch().length;
            if (affectedRows!=event.get_categories().size()){
                connection.rollback();
                throw new SQLException("Failed to insert categories for event. Rolledback");
            }

            //insert permmisions
            cur = connection.createStatement();
            for (String sp : dbEvent.insertUserPermissions())
                cur.addBatch(sp);
            affectedRows = cur.executeBatch().length;
            if (affectedRows != event.get_users().size()){
                connection.rollback();
                throw new SQLException("Failed to insert permissions. Rolledback");
            }

            //insert event update
            cur = connection.createStatement();
            affectedRows = cur.executeUpdate(dbEvent.updateInEvent());
            if (affectedRows != 1){
                connection.rollback();
                throw new SQLException("Failed to insert update of event. Rolledback");
            }

            //insert accountables
            cur = connection.createStatement();
            for (String sa : dbEvent.accountableUsers())
                cur.addBatch(sa);
            affectedRows = cur.executeBatch().length;
            if (affectedRows != event.get_organizations().size()){
                connection.rollback();
                throw new SQLException("Failed to create accountables. Rolledback");
            }

            connection.commit();

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }


    public static void main(String[] args) {
        Model m = new Model();
        List<AUser> users = new ArrayList<>();
        users.add(new User("hagai2"));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("cat1"));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization(users, "org1"));
        Event e = new Event("test event", categories, new User("hagai"), "test update", organizations);
        DBEvent idbAdapter = new DBEvent(e);
//        int i = m.makeInsert(idbAdapter);
//        e.set_id(i);
//        idbAdapter.set_statusOk();
        m.addEvent(e);

    }
}
