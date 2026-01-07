import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object (DAO) for the 'duty' table.
 * Handles database operations for Duty entities.
 */
public class DutyDAO {

    // --- READ Operations ---
    
    /**
     * Retrieves all duties from the database, wrapped in a JavaFX ObservableList.
     * @return An ObservableList of all Duty objects.
     */
    public ObservableList<Duty> getAllDuties() {
        ObservableList<Duty> duties = FXCollections.observableArrayList();
        String sql = "SELECT * FROM duty";
        
        try (Connection conn = DBConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                duties.add(mapResultSetToDuty(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all duties: " + e.getMessage());
            e.printStackTrace();
        }
        return duties;
    }

    /**
     * Retrieves a single Duty by its ID.
     * @param id The ID of the duty.
     * @return The Duty object, or null if not found.
     */
    public Duty getDutyById(int id) {
        String sql = "SELECT * FROM duty WHERE id = ?";
        try (Connection conn = DBConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDuty(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting duty by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // --- CREATE Operation ---

    /**
     * Adds a new Duty to the database.
     * @param duty The Duty object to add.
     */
    public void addDuty(Duty duty) {
        String sql = "INSERT INTO duty (time, location, flight_no) VALUES (?, ?, ?)";
        
        try (Connection conn = DBConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setDutyPreparedStatement(pstmt, duty);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error adding duty: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- UPDATE Operation ---

    /**
     * Updates an existing Duty in the database.
     * @param duty The Duty object with updated fields (identified by ID).
     */
    public void updateDuty(Duty duty) {
        String sql = "UPDATE duty SET time = ?, location = ?, flight_no = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setDutyPreparedStatement(pstmt, duty);
            pstmt.setInt(4, duty.getId()); // Assuming Duty has getId()
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating duty: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- DELETE Operation ---

    /**
     * Deletes a Duty from the database by its ID.
     * @param id The ID of the duty to delete.
     */
    public void deleteDuty(int id) {
        String sql = "DELETE FROM duty WHERE id = ?";
        
        try (Connection conn = DBConnection.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting duty: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // --- Private Helper Methods ---

    /**
     * Helper to map a database ResultSet row to a Duty object.
     */
    private Duty mapResultSetToDuty(ResultSet rs) throws SQLException {
        // Assumes a Duty class constructor exists with id
        return new Duty(
            rs.getInt("id"),
            rs.getString("time"),
            rs.getString("location"),
            rs.getInt("flight_no")
        );
    }

    /**
     * Helper to set all parameters for a Duty PreparedStatement (used by addDuty and updateDuty).
     */
    private void setDutyPreparedStatement(PreparedStatement pstmt, Duty duty) throws SQLException {
        pstmt.setString(1, duty.getTime());
        pstmt.setString(2, duty.getLoc());
        pstmt.setInt(3, duty.getflightNo());
    }
}