import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the 'user' table (note: table name is 'user' in MySQL schema).
 * Handles database operations for User entities.
 */
public class UserDAO {
    
    // --- READ Operations ---

    /**
     * Retrieves a single User by their unique encryption key.
     * @param key The unique encryp_key of the user.
     * @return The populated User object, or null if not found.
     */
    public User getUserByEncrypKey(String key) {
        String sql = "SELECT * FROM user WHERE encryp_key = ?";
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
     * @return A List of all User objects.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        
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
     * Adds a new User to the database.
     * @param user The User object to add.
     */
    public void addUser(User user) {
        String sql = "INSERT INTO user (name, cnic, email, contact, passport_number, citizenship, visa, role, encryp_key, " +
                     "bank_name, bank_acc, salary, prof_img_path, country, city, post_code) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setUserPreparedStatement(pstmt, user);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- UPDATE Operation ---

    /**
     * Updates an existing User in the database.
     * @param user The User object with updated fields (identified by encryp_key).
     */
    public void updateUser(User user) {
        String sql = "UPDATE user SET name = ?, cnic = ?, email = ?, contact = ?, passport_number = ?, citizenship = ?, " +
                     "visa = ?, role = ?, bank_name = ?, bank_acc = ?, salary = ?, prof_img_path = ?, " +
                     "country = ?, city = ?, post_code = ? " +
                     "WHERE encryp_key = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setUserPreparedStatement(pstmt, user);
            pstmt.setString(16, user.getencrypKey()); // For WHERE clause
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- DELETE Operation ---

    /**
     * Deletes a User from the database by their encryp_key.
     * @param key The unique encryp_key of the user to delete.
     */
    public void deleteUserByEncrypKey(String key) {
        String sql = "DELETE FROM user WHERE encryp_key = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, key);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- Private Helper Methods ---

    /**
     * Helper to map a database ResultSet row to a User object.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
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

        // Fetch assigned flights and duties
        int userId = rs.getInt("id");
        user.getAssignedFlights().addAll(getAssignedFlightsForUser(userId));
        user.getAssignedDuties().addAll(getAssignedDutiesForUser(userId));

        return user;
    }

    /**
     * Fetches assigned Flights for a given user ID.
     */
    private List<Flight> getAssignedFlightsForUser(int userId) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT f.* FROM flight f " +
                     "INNER JOIN user_flight uf ON f.flight_no = uf.flight_no " +
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
     * Fetches assigned Duties for a given user ID.
     */
    private List<Duty> getAssignedDutiesForUser(int userId) {
        List<Duty> duties = new ArrayList<>();
        String sql = "SELECT d.* FROM duty d " +
                     "INNER JOIN user_duty ud ON d.id = ud.duty_id " +
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
     * Helper to set all parameters for a User PreparedStatement (used by addUser and updateUser).
     */
    private void setUserPreparedStatement(PreparedStatement pstmt, User user) throws SQLException {
        int i = 1;
        pstmt.setString(i++, user.getName());
        pstmt.setString(i++, user.getCnic());
        pstmt.setString(i++, user.getEmail());
        pstmt.setString(i++, user.getContact());
        pstmt.setString(i++, user.getPassportNumber());
        pstmt.setString(i++, user.getCitizenship());
        pstmt.setString(i++, user.getVisa());
        pstmt.setString(i++, user.getRole());
        if (pstmt.getParameterMetaData().getParameterCount() > 8) { // For addUser (includes encryp_key)
            pstmt.setString(i++, user.getencrypKey());
        }
        pstmt.setString(i++, user.getBankName());
        pstmt.setString(i++, user.getbankAcc());
        pstmt.setDouble(i++, user.getSalary());
        pstmt.setString(i++, user.getprofImgPath());
        pstmt.setString(i++, user.getCountry());
        pstmt.setString(i++, user.getCity());
        pstmt.setString(i++, user.getpostCode());
    }

    /**
     * Maps a ResultSet row to a Flight object.
     */
    private Flight mapResultSetToFlight(ResultSet rs) throws SQLException {
        return new Flight(
            rs.getInt("flight_no"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getString("schedule"),
            rs.getString("status"),
            rs.getString("type")
        );
    }

    /**
     * Maps a ResultSet row to a Duty object.
     */
    private Duty mapResultSetToDuty(ResultSet rs) throws SQLException {
        return new Duty(
            rs.getInt("id"),
            rs.getString("time"),
            rs.getString("location"),
            rs.getInt("flight_no")
        );
    }
}