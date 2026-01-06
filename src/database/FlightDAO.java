import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List; // Added missing import

/**
 * Data Access Object (DAO) for the 'flights' table.
 * Handles database operations for Flight entities.
 */
public class FlightDAO {

    // --- READ Operations ---
    
    /**
     * Retrieves all flights from the database, wrapped in a JavaFX ObservableList.
     * * @return An ObservableList of all Flight objects.
     */
    public ObservableList<Flight> getAllFlights() {
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flights";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                flights.add(mapResultSetToFlight(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all flights: " + e.getMessage());
            e.printStackTrace();
        }
        return flights;
    }

    /**
     * Retrieves a single Flight by its flight number.
     * * @param flightNo The primary key of the flight.
     * * @return The Flight object, or null if not found.
     */
    public Flight getFlightByNumber(int flightNo) {
        String sql = "SELECT * FROM flights WHERE flight_no = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightNo);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFlight(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting flight by number: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a flight number already exists in the database.
     * * @param flightNo The flight number to check.
     * * @return true if the flight number exists, false otherwise.
     */
    public boolean flightNoExists(int flightNo) {
        String sql = "SELECT 1 FROM flights WHERE flight_no = ?"; // Efficient check
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking flight number existence: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // --- CREATE Operation ---

    /**
     * Adds a new flight record to the database.
     * * @param flight The Flight object to insert.
     */
    public void addFlight(Flight flight) {
        String sql = "INSERT INTO flights (flight_no, origin, dest, schedule, status, type) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            setFlightPreparedStatement(pstmt, flight);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error adding flight: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- UPDATE Operation ---

    /**
     * Updates an existing flight record based on its flight number.
     * * @param flight The Flight object with new data and the existing flight_no.
     */
    public void updateFlight(Flight flight) {
        String sql = "UPDATE flights SET origin = ?, dest = ?, schedule = ?, status = ?, type = ? " +
                     "WHERE flight_no = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Set all fields except the WHERE clause
            // Note: The original implementation of setFlightPreparedStatement has 6 parameters.
            // We need to adjust the parameter setting for the UPDATE query structure (5 fields + 1 WHERE).
            
            pstmt.setString(1, flight.getOrigin());
            pstmt.setString(2, flight.getdest());
            pstmt.setString(3, flight.getSchedule());
            pstmt.setString(4, flight.getStatus());
            pstmt.setString(5, flight.getType());
            
            // The parameter for the WHERE clause
            pstmt.setInt(6, flight.getflightNo()); 
            
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error updating flight: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // --- DELETE Operation ---

    /**
     * Deletes a flight record based on its flight number.
     * * @param flightNo The flight number of the record to delete.
     */
    public void deleteFlight(int flightNo) {
        String sql = "DELETE FROM flights WHERE flight_no = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, flightNo);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting flight: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // --- Private Helper Methods ---

    /**
     * Helper to map a database ResultSet row to a Flight object.
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
     * Helper to set all parameters for a Flight PreparedStatement (used by addFlight).
     */
    private void setFlightPreparedStatement(PreparedStatement pstmt, Flight flight) throws SQLException {
        // This helper is correctly used by addFlight (6 parameters)
        pstmt.setInt(1, flight.getflightNo());
        pstmt.setString(2, flight.getOrigin());
        pstmt.setString(3, flight.getdest());
        pstmt.setString(4, flight.getSchedule());
        pstmt.setString(5, flight.getStatus());
        pstmt.setString(6, flight.getType());
    }
}