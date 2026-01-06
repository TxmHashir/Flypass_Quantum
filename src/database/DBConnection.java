import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class for managing the database connection and initialization.
 * Uses SQLite as the database engine.
 */
public class DBConnection {

    // --- Configuration ---
    // The connection string for the SQLite database file.
    private static final String DB_URL = "jdbc:sqlite:flypass_quantum.db";

    /**
     * Establishes a connection to the SQLite database and initializes the schema 
     * (creates tables) if they do not exist.
     * * @return A valid java.sql.Connection object, or null if an error occurs.
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Attempt to establish the connection
            conn = DriverManager.getConnection(DB_URL);
            
            // Initialize the database structure (create tables)
            initializeDatabase(conn); 
            
        } catch (SQLException e) {
            // Handle connection errors
            System.err.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * Executes SQL statements to create all necessary tables if they don't already exist.
     * * @param conn The active database connection.
     */
    private static void initializeDatabase(Connection conn) {
        
        // Array of SQL CREATE TABLE statements.
        String[] sqlStatements = {
            
            // 1. Users Table: Stores details for all system users (staff, pilots, customers, etc.).
            "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "cnic TEXT UNIQUE NOT NULL, " +
                "email TEXT UNIQUE NOT NULL, " +
                "contact TEXT, " +
                "passport_number TEXT, " +
                "citizenship TEXT, " +
                "visa TEXT, " +
                // Role is restricted to a set of predefined values.
                "role TEXT NOT NULL CHECK (role IN ('admin', 'pilot', 'air_hostess', 'staff', 'customer')), " + 
                "encryp_key TEXT UNIQUE NOT NULL, " + // Key for password/data encryption
                "bank_name TEXT, " +
                "bank_acc TEXT, " +
                "salary REAL DEFAULT 0.0, " +
                "prof_img_path TEXT DEFAULT 'titleicon.png', " +
                "country TEXT, " +
                "city TEXT, " +
                "post_code TEXT" +
            ");",
            
            // 2. Flights Table: Stores flight information.
            "CREATE TABLE IF NOT EXISTS flights (" +
                "flight_no INTEGER PRIMARY KEY, " +
                "origin TEXT NOT NULL, " +
                "dest TEXT NOT NULL, " +
                "schedule TEXT NOT NULL, " +
                "status TEXT NOT NULL, " +
                // Flight type is restricted to Domestic or International.
                "type TEXT NOT NULL CHECK (type IN ('Domestic', 'International'))" +
            ");",
            
            // 3. Duties Table: Stores specific duty assignments (e.g., ground duties).
            "CREATE TABLE IF NOT EXISTS duties (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time TEXT NOT NULL, " +
                "location TEXT NOT NULL, " +
                "flight_no INTEGER NOT NULL, " +
                // Foreign key linking duty to a specific flight.
                "FOREIGN KEY (flight_no) REFERENCES flights(flight_no) ON DELETE CASCADE" +
            ");",
            
            // 4. User-Flight Assignments (Junction Table): Links users (staff/crew) to flights.
            "CREATE TABLE IF NOT EXISTS user_flights (" +
                "user_id INTEGER NOT NULL, " +
                "flight_no INTEGER NOT NULL, " +
                "PRIMARY KEY (user_id, flight_no), " + // Composite Primary Key
                // Foreign keys with CASCADE delete for cleanup.
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (flight_no) REFERENCES flights(flight_no) ON DELETE CASCADE" +
            ");",
            
            // 5. User-Duty Assignments (Junction Table): Links users (staff/crew) to specific duties.
            "CREATE TABLE IF NOT EXISTS user_duties (" +
                "user_id INTEGER NOT NULL, " +
                "duty_id INTEGER NOT NULL, " +
                "PRIMARY KEY (user_id, duty_id), " + // Composite Primary Key
                // Foreign keys with CASCADE delete for cleanup.
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (duty_id) REFERENCES duties(id) ON DELETE CASCADE" +
            ");"
        };

        try (Statement stmt = conn.createStatement()) {
            // Execute each creation statement sequentially
            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            // Handle errors during table creation
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}