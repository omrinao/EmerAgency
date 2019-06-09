package Models;


import Controller.Controller;
import DBAdapters.DBEvent;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Model {
    //Const
    private final String DB_URL = "jdbc:sqlite:resources/db.db";

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

    /**
     * get user from login
     * @param username - username
     * @param password - password
     * @return - AUser if success, null if failed
     */
    public AUser login(String username, String password){
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            User user = null;
            if (rs.next()) {
                user = new User(rs.getString(1));
                user.set_password(rs.getString(2));
                user.set_email(rs.getString(3));
                user.set_status(AccountStatus.valueOf(rs.getString(4)));
                user.set_loginErr(rs.getInt(5));
                user.set_organiztion(Controller.getOrg(rs.getString(6)));
                user.set_rank(rs.getInt(7));
            }

            return user;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    /**
     * method to add an event to the DB
     * @param event - the event to be added
     * @return - true for success, false otherwise
     */
    public boolean addEvent(Event event) {
        int affectedRows = -1;
        int id = -1;
        DBEvent dbEvent = new DBEvent(event);
        String sEvent = dbEvent.insertEvents();

        try (Connection connection = make_connection();
             PreparedStatement insertEvent = connection.prepareStatement(sEvent)) {
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

    /**
     * getter for events of a given category
     * @param cat - list of categories
     * @return - list of events
     */
    public List<Event> watchEvents(List<Category> cat){
        String sq = "select * from 'categoriesInEvents' where category in (" + cat.toString().substring(1, cat.toString().length()-1) + ")";

        try (Connection connection = make_connection();
        PreparedStatement getEventsIds = connection.prepareStatement(sq)){

            HashMap<Integer, Event> events = new HashMap<>();
            HashMap<Integer, Event> updates = new HashMap<>();

            ResultSet rs = getEventsIds.executeQuery();

            ArrayList<Integer> eventIds = new ArrayList<>();
            while (rs.next()){
                int eventId = rs.getInt(1);
                events.putIfAbsent(eventId, new Event());
                Event ev = events.get(eventId);
                ev.set_id(eventId);
                ev.add_category(rs.getString(2));
                eventIds.add(eventId);
            }

            String se = "select * from 'events' where id IN (" + eventIds.toString().substring(1, eventIds.toString().length()-1) +")";
            PreparedStatement idStatement = connection.prepareStatement(se);
            rs = idStatement.executeQuery();

            while (rs.next()){
                Event ev = events.get(rs.getInt(1));
//                ev.set_id(rs.getInt(1));
                ev.set_title(rs.getString(2));
                ev.set_published(rs.getString(3));
                ev.set_status(EventStatus.valueOf(rs.getString(4)));
                ev.set_creator(new User(rs.getString(5)));

//                events.put(rs.getInt(1), ev);
            }

            List<Integer> updateIds = new ArrayList<>();
            String seu = "select * from 'eventsUpdates' where eventId IN (" + eventIds.toString().substring(1, eventIds.toString().length()-1) +")";
            PreparedStatement updatesIdStatement = connection.prepareStatement(seu);
            rs = updatesIdStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(2);
                updateIds.add(id);
                Event ev = events.get(rs.getInt(1));
                ev.add_update(new Update(ev, id));
                updates.put(id, ev);
            }

            String su = "select * from 'updates' where id IN (" + updateIds.toString().substring(1, updateIds.toString().length()-1) +")";
            PreparedStatement updatesInfoStatement = connection.prepareStatement(su);
            rs = updatesInfoStatement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                Event ev = updates.get(id);
                Update up = ev.get_update(id);
                up.set_published(rs.getString(2));
                up.set_description(rs.getString(4));
            }

            String sorg = "select * from 'accountableUsers' where eventId IN (" + eventIds.toString().substring(1, eventIds.toString().length()-1) +")";
            PreparedStatement orgStatement = connection.prepareStatement(sorg);
            rs = orgStatement.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                Event ev = events.get(id);
                ev.get_organizations().put(Controller.getOrg(rs.getString(2)), new User(rs.getString(3)));
            }


            List<Event> ret = new ArrayList<>(events.values());
            ret.sort(new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return o1.get_id() - o2.get_id();
                }
            });

            return ret;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * used to add update on an event
     * @param update - the update needed
     * @return true for success, false otherwise
     */
    public boolean addUpdate(Update update){
        List<Update> up = update.get_event().get_updates();
        String addUpdate = "insert into 'updates' values(NULL,'" + update.get_published() + "','" + up.get(up.size()-1).get_id() + "','" + update.get_description() + "');";
        int affectedRows = -1;
        int key = -1;

        try (Connection connection = make_connection();
        PreparedStatement cur = connection.prepareStatement(addUpdate)){
            connection.setAutoCommit(false);

            affectedRows = cur.executeUpdate();
            if (affectedRows!=1){
                connection.rollback();
                throw  new SQLException("Failed to insert update. Rolledback");
            }else {
                key = cur.getGeneratedKeys().getInt(1);
                update.set_id(key);
            }

            PreparedStatement statement = connection.prepareStatement("insert into 'eventsUpdates' values('"  + update.get_event().get_id() + "','" + update.get_id() + "','" + (up.size()+1) + "');" );
            affectedRows = statement.executeUpdate();
            if (affectedRows!=1){
                connection.rollback();
                throw  new SQLException("Failed to insert to table. Rolledback");
            }

            connection.commit();



            return true;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Model m = new Model();
        List<AUser> users = new ArrayList<>();
        users.add(new User("hagai2"));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Robbery"));
        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization(users, "org1"));
        Event e = new Event("test event", categories, new User("hagai"), "test update", organizations);
        e.set_id(4);
        e.get_updates().get(0).set_id(5);
        DBEvent idbAdapter = new DBEvent(e);
//        int i = m.makeInsert(idbAdapter);
//        e.set_id(i);
//        idbAdapter.set_statusOk();
//        m.addEvent(e);

        m.addUpdate(new Update(e, null, LocalDateTime.now(), "test update 2"));
        try {
            Controller c = Controller.getInstance();
        }
        catch (Exception ex){
            System.out.println("lala");
        }



        List<Event> el = m.watchEvents(categories);
        System.out.println(el);

//        LocalDateTime d1 = LocalDateTime.now();
//        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        TemporalAccessor s1 = df.parse(df.format(LocalDateTime.now()));
//        LocalDateTime d12 = LocalDateTime.from(df.parse(df.format(LocalDateTime.now())));
//
//        System.out.println(d12.toString());
//        System.out.println(df.format(d12));
//        System.out.println(df.parse(df.format(d12)));
//        System.out.println(d12.equals(LocalDateTime.from(df.parse(df.format(d12)))));



    }


    /**
     * getter for all organizations
     * @return - list of organizations and their users
     * @throws SQLException - if failed to retrieve
     */
    public List<Organization> getOrganizations() throws SQLException {
        try (Connection connection = make_connection();){
            HashMap<String, Organization> tmp = new HashMap<>();

            String query = "select * from users inner join organizations on users.organization = organizations.name order by users.organization asc";
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()){
                String name = rs.getString(8);
                tmp.putIfAbsent(name, new Organization(new ArrayList<>(), name));

                User user = new User(rs.getString(1));
                user.set_password(rs.getString(2));
                user.set_email(rs.getString(3));
                user.set_status(AccountStatus.valueOf(rs.getString(4)));
                user.set_loginErr(rs.getInt(5));
                user.set_organiztion(tmp.get(name));
                user.set_rank(rs.getInt(7));

                tmp.get(name).add_user(user);
            }

            return new ArrayList<>(tmp.values());
        }
    }


    /**
     * getter for all categories
     * @return - a list of categories
     */
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
