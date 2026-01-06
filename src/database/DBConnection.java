import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/flypass_quantum?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASS = "One1_Two2"; 

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            initializeDatabase(conn); 
            
        } catch (SQLException e) {
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    private static void initializeDatabase(Connection conn) {
        String[] sqlStatements = {
            //user
            "CREATE TABLE IF NOT EXISTS user (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL, " +
                "cnic VARCHAR(20) UNIQUE NOT NULL, " +
                "email VARCHAR(255) UNIQUE NOT NULL, " +
                "contact VARCHAR(50), " +
                "passport_number VARCHAR(50), " +
                "citizenship VARCHAR(100), " +
                "visa VARCHAR(255), " + // Stored as "Type, Country" e.g., "Tourist, USA"
                "role ENUM('admin', 'pilot', 'customer') NOT NULL, " +
                "encryp_key VARCHAR(255) UNIQUE NOT NULL, " +
                "bank_name VARCHAR(255), " +
                "bank_acc VARCHAR(100), " +
                "salary DECIMAL(15,2) DEFAULT 0.00, " +
                "prof_img_path VARCHAR(512) DEFAULT 'titleicon.png', " +
                "country VARCHAR(100), " +
                "city VARCHAR(100), " +
                "post_code VARCHAR(20)" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
            //flight
            "CREATE TABLE IF NOT EXISTS flight (" +
                "flight_no INT PRIMARY KEY, " +
                "origin VARCHAR(100) NOT NULL, " +
                "destination VARCHAR(100) NOT NULL, " +
                "schedule VARCHAR(50) NOT NULL, " +
                "status VARCHAR(50) NOT NULL, " +
                "type ENUM('Domestic', 'International') NOT NULL" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
            //duty
            "CREATE TABLE IF NOT EXISTS duty (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "time VARCHAR(50) NOT NULL, " +
                "location VARCHAR(255) NOT NULL, " +
                "flight_no INT NOT NULL, " +
                "FOREIGN KEY (flight_no) REFERENCES flight(flight_no) ON DELETE CASCADE" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
            // User-Flight
            "CREATE TABLE IF NOT EXISTS user_flight (" +
                "user_id INT NOT NULL, " +
                "flight_no INT NOT NULL, " +
                "PRIMARY KEY (user_id, flight_no), " +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (flight_no) REFERENCES flight(flight_no) ON DELETE CASCADE" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",
            // User-Duty
            "CREATE TABLE IF NOT EXISTS user_duty (" +
                "user_id INT NOT NULL, " +
                "duty_id INT NOT NULL, " +
                "PRIMARY KEY (user_id, duty_id), " +
                "FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (duty_id) REFERENCES duty(id) ON DELETE CASCADE" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;",

            "CREATE INDEX IF NOT EXISTS idx_user_encryp_key ON user(encryp_key);",
            "CREATE INDEX IF NOT EXISTS idx_flight_origin_dest ON flight(origin, destination);"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}