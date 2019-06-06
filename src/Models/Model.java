package Models;


import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

    /**
     * checks if a given username already exist
     *
     * @param userName - the given username to check
     * @return - true if exist, false otherwise
     */
    private boolean user_exist(String userName) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";

        try (Connection conn = this.make_connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userName);
            m_results = pstmt.executeQuery();

            if (!m_results.next()) {
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }


    public static void main(String[] args){
        Model m = new Model();
        System.out.println(System.getProperty("user.dir"));
        Connection connection = m.make_connection();

        System.out.println("lalal");
    }
}
