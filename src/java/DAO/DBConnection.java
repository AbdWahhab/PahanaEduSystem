package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/pahana_edu?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";  // or your DB password

    public static Connection getConnection() {
        try {
            System.out.println("Loading MySQL JDBC Driver...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Successfully.");

            System.out.println("Connecting to database: " + URL);
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database Connection Established.");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("❌ JDBC Driver not found. Did you add mysql-connector-j to your project?");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ SQL Connection Error:");
            System.err.println("Message: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("ErrorCode: " + e.getErrorCode());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("❌ Unexpected Error:");
            e.printStackTrace();
            return null;
        }
    }
}