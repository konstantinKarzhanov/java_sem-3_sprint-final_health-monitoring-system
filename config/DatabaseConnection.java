package config;

// Import required packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Define class
public class DatabaseConnection {
    // Define attributes
    private static final String URL = "jdbc:postgresql://localhost:5432/healthMonitoringSystem";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    // Define method to reuse connection
    public static Connection useConnection() {
        Connection dbConnection = null;
        
        try {
            // use Postgres Driver
            // Class.forName("org.postgresql.Driver"); // in this case need to catch "ClassNotFoundException" as well
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return dbConnection;
    }
}
