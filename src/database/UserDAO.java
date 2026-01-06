import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the 'users' table.
 * Handles database operations for User entities.
 */
public class UserDAO {
    
    // --- READ Operations ---

    /**
     * Retrieves a single User by their unique encryption key.
     * * @param key The unique encryp_key of the user.
     * * @return The populated User object, or null if not found.
     */
    public User getUserByEncrypKey(String key) {
        String sql = "SELECT * FROM users WHERE encryp_key = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, key);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting user by key: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     * * @return A list of all User objects.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    // --- CREATE Operation ---

    /**
     * Inserts a new user record into the database (User Sign Up).
     * * @param user The User object containing data to insert.
     * * @return true if sign-up was successful, false otherwise.
     */
    public boolean signUp(User user) {
        // Note: The 'id' column is auto-incremented and excluded from the insert list.
        String sql = "INSERT INTO users (name, cnic, email, contact, passport_number, citizenship, visa, role, encryp_key, " +
                     "bank_name, bank_acc, salary, prof_img_path, country, city, post_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set all parameters using the helper method.
            // (isUpdate=false means the encryp_key is the last parameter set)
            setUserPreparedStatement(pstmt, user, false);
            pstmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error during user sign-up: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // --- UPDATE Operation ---

    /**
     * Updates an existing user record based on the unique encryp_key.
     * The ID, role, and encryp_key are typically not updated here.
     * * @param user The User object with the new data.
     */
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, cnic = ?, email = ?, contact = ?, passport_number = ?, citizenship = ?, " +
                     "visa = ?, role = ?, bank_name = ?, bank_acc = ?, salary = ?, prof_img_path = ?, " +
                     "country = ?, city = ?, post_code = ? WHERE encryp_key = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set all parameters.
            // (isUpdate=true means the encryp_key is set as the WHERE clause parameter)
            setUserPreparedStatement(pstmt, user, true); 
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- DELETE Operation ---

    /**
     * Deletes a user record based on the unique encryp_key.
     * * @param user The User object to delete (only needs the encryp_key).
     */
    public void deleteUser(User user) {
        String sql = "DELETE FROM users WHERE encryp_key = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getencrypKey());
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // --- Private Mapping and Relationship Helpers ---

    /**
     * Helper to map a database ResultSet row to a fully populated User object, 
     * including assigned flights and duties.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        // Set basic user attributes
        user.setName(rs.getString("name"));
        user.setCnic(rs.getString("cnic"));
        user.setEmail(rs.getString("email"));
        user.setContact(rs.getString("contact"));
        user.setPassportNumber(rs.getString("passport_number"));
        user.setCitizenship(rs.getString("citizenship"));
        user.setVisa(rs.getString("visa"));
        user.setRole(rs.getString("role"));
        user.setencrypKey(rs.getString("encryp_key"));
        user.setBankName(rs.getString("bank_name"));
        user.setbankAcc(rs.getString("bank_acc"));
        user.setSalary(rs.getDouble("salary"));
        user.setprofImgPath(rs.getString("prof_img_path"));
        user.setCountry(rs.getString("country"));
        user.setCity(rs.getString("city"));
        user.setpostCode(rs.getString("post_code"));

        // Load assigned flights and duties using separate JOIN queries
        int userId = rs.getInt("id"); // Retrieve the user's primary key (ID)
        user.getAssignedFlights().addAll(getAssignedFlights(userId));
        user.getAssignedDuties().addAll(getAssignedDuties(userId));
        
        return user;
    }

    /**
     * Retrieves all flights assigned to a specific user using the 'user_flights' junction table.
     */
    private List<Flight> getAssignedFlights(int userId) {
        List<Flight> flights = new ArrayList<>();
        // Uses a JOIN to fetch flight details linked by user_id
        String sql = "SELECT f.* FROM flights f " +
                     "JOIN user_flights uf ON f.flight_no = uf.flight_no " +
                     "WHERE uf.user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    flights.add(mapResultSetToFlight(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting assigned flights: " + e.getMessage());
            e.printStackTrace();
        }
        return flights;
    }

    /**
     * Retrieves all duties assigned to a specific user using the 'user_duties' junction table.
     */
    private List<Duty> getAssignedDuties(int userId) {
        List<Duty> duties = new ArrayList<>();
        // Uses a JOIN to fetch duty details linked by user_id
        String sql = "SELECT d.* FROM duties d " +
                     "JOIN user_duties ud ON d.id = ud.duty_id " +
                     "WHERE ud.user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    duties.add(mapResultSetToDuty(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting assigned duties: " + e.getMessage());
            e.printStackTrace();
        }
        return duties;
    }

    /**
     * Helper to set all parameters for a User PreparedStatement (used by signUp and updateUser).
     * * @param isUpdate If true, sets the encryp_key as the last parameter for the WHERE clause.
     */
    private void setUserPreparedStatement(PreparedStatement pstmt, User user, boolean isUpdate) throws SQLException {
        int i = 1;
        pstmt.setString(i++, user.getName());
        pstmt.setString(i++, user.getCnic());
        pstmt.setString(i++, user.getEmail());
        pstmt.setString(i++, user.getContact());
        pstmt.setString(i++, user.getPassportNumber());
        pstmt.setString(i++, user.getCitizenship());
        pstmt.setString(i++, user.getVisa());
        pstmt.setString(i++, user.getRole());
        
        // Note: The original code had encryp_key here for insert, but the SQL query 
        // in signUp has it later (position 9). Adjusting the original logic to match the SQL structure.
        if (!isUpdate) { // For INSERT, encryp_key is position 9 in the original SQL.
             pstmt.setString(i++, user.getencrypKey()); // This is parameter 9
        }
        
        pstmt.setString(i++, user.getBankName()); // Parameter 10/9
        pstmt.setString(i++, user.getbankAcc()); // Parameter 11/10
        pstmt.setDouble(i++, user.getSalary()); // Parameter 12/11
        pstmt.setString(i++, user.getprofImgPath()); // Parameter 13/12
        pstmt.setString(i++, user.getCountry()); // Parameter 14/13
        pstmt.setString(i++, user.getCity()); // Parameter 15/14
        pstmt.setString(i++, user.getpostCode()); // Parameter 16/15
        
        if (isUpdate) { // For UPDATE, encryp_key is set for the WHERE clause (last parameter)
            pstmt.setString(i, user.getencrypKey()); // Parameter 16
        } else {
             // Re-setting the encryp_key here to align with the original signUp SQL logic
             // which has 16 parameters in total.
             // Original: encryp_key is the 9th column in the SQL, but the helper sets it 16th if not updated.
             // ***Correction***: The original logic was flawed in the helper method. 
             // Fixing the parameter setting to match the `signUp` SQL structure.
             // Assuming the `signUp` SQL structure provided is correct (16 params, encryp_key is 9th).
        }
        // Note: The logic in the original setUserPreparedStatement helper was inconsistent with the SQL.
        // The refined code above (using i++) attempts to fix this parameter count issue.
    }

    /**
     * Maps a ResultSet row to a Flight object. (Required because UserDAO fetches related Flights)
     */
    private Flight mapResultSetToFlight(ResultSet rs) throws SQLException {
        // Assumes a Flight class constructor exists
        return new Flight(
            rs.getInt("flight_no"),
            rs.getString("origin"),
            rs.getString("dest"),
            rs.getString("schedule"),
            rs.getString("status"),
            rs.getString("type")
        );
    }

    /**
     * Maps a ResultSet row to a Duty object. (Required because UserDAO fetches related Duties)
     */
    private Duty mapResultSetToDuty(ResultSet rs) throws SQLException {
        // Assumes a Duty class constructor exists
        return new Duty(
            rs.getString("time"),
            rs.getString("location"),
            rs.getInt("flight_no")
        );
    }
}