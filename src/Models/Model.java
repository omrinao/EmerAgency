package Models;


import DBAdapters.DBEvent;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Model {
    //Const
    private final String DB_URL = "jdbc:sqlite:resources/db.db";

    // helpful attributes
    private ResultSet m_results;

    public Model() {

    }

    /**
     * a method to return a connection with the Database
     *
     * @return - a connection if success, null otherwise
     */
    private Connection make_connection() {

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


    public void watchEvents(List<Category> cat){
        String sq = "select * from 'categoriesInEvents' where category in (" + cat.toString().substring(1, cat.toString().length()-1) + ")";

        try (Connection connection = make_connection();
        PreparedStatement getEventsIds = connection.prepareStatement(sq)){

            List<Event> ret = new ArrayList<>();

            ResultSet rs = getEventsIds.executeQuery();

            List<Integer> eventIds = new ArrayList<>();
            while (rs.next()){
                eventIds.add(rs.getInt(1));
            }

            String se = "select * from 'events' where id IN (" + eventIds.toString().substring(1, eventIds.toString().length()-1) +")";
            PreparedStatement idStatement = connection.prepareStatement(se);
            rs = getEventsIds.executeQuery();

            while (rs.next()){
                Event ev = new Event();
                ev.set_id(rs.getInt(1));
                ev.set_published(new Date());
                ev.set_creator(new User(rs.getString(5)));

                ret.add(ev);
            }




        }
        catch (SQLException ex){

        }
    }

    public static void main(String[] args) {
        Model m = new Model();
//        List<AUser> users = new ArrayList<>();
//        users.add(new User("hagai2"));
//        List<Category> categories = new ArrayList<>();
//        categories.add(new Category("cat1"));
//        List<Organization> organizations = new ArrayList<>();
//        organizations.add(new Organization(users, "org1"));
//        Event e = new Event("test event", categories, new User("hagai"), "test update", organizations);
//        DBEvent idbAdapter = new DBEvent(e);
////        int i = m.makeInsert(idbAdapter);
////        e.set_id(i);
////        idbAdapter.set_statusOk();
//        m.addEvent(e);


//        m.watchEvents(categories);

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date d = new Date();
        Date d2 = null;
        try {
            d2 = dateFormat.parse(dateFormat.format(d));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(d);
        System.out.println(d2);

    }


    public List<Organization> getOrganizations() {
        Organization police = new Organization(new ArrayList<AUser>(), "Police");
        Organization mda = new Organization(new ArrayList<AUser>(), "MDA");
        Organization firefighters = new Organization(new ArrayList<AUser>(), "Fire Figthters");

        List<Organization> organizations = new ArrayList<Organization>();
        organizations.add(police);
        organizations.add(mda);
        organizations.add(firefighters);

        return organizations;
    }


    public List<Category> getCategories() {
        Category robbery = new Category("Robbery");
        Category murder = new Category("Murder");
        Category mci = new Category("MCI"); // multiple casualty incident
        Category fire = new Category("Fire");
        Category kidnapping = new Category("Kidnapping");

        List<Category> categories = new ArrayList<Category>();
        categories.add(robbery);
        categories.add(murder);
        categories.add(mci);
        categories.add(fire);
        categories.add(kidnapping);

        return categories;
    }
}
