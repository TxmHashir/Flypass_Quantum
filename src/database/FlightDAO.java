import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class FlightDAO {

    // --- READ Operations ---
    public ObservableList<Flight> getAllFlights() {
        ObservableList<Flight> flights = FXCollections.observableArrayList();
        String sql = "SELECT * FROM flight";
        
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

    public Flight getFlightByNo(int flightNo) {
        String sql = "SELECT * FROM flight WHERE flight_no = ?";
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

    // --- CREATE Operation ---

    public void addFlight(Flight flight) {
        String sql = "INSERT INTO flight (flight_no, origin, destination, schedule, status, type, price) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
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
   public void updateFlight(Flight flight) {
    String sql = "UPDATE flight SET origin = ?, destination = ?, schedule = ?, status = ?, type = ?, price = ? WHERE flight_no = ?"; 
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setString(1, flight.getOrigin());
        pstmt.setString(2, flight.getdest());
        pstmt.setString(3, flight.getSchedule());
        pstmt.setString(4, flight.getStatus());
        pstmt.setString(5, flight.getType());
        pstmt.setDouble(6, flight.getPrice());
        pstmt.setInt(7, flight.getflightNo()); 
        
        pstmt.executeUpdate();
        
    } catch (SQLException e) {
        System.err.println("Error updating flight: " + e.getMessage());
        e.printStackTrace();
    }
}
    // --- DELETE Operation ---
    public void deleteFlight(int flightNo) {
        String sql = "DELETE FROM flight WHERE flight_no = ?";
        
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
    private Flight mapResultSetToFlight(ResultSet rs) throws SQLException {
        return new Flight(
            rs.getInt("flight_no"),
            rs.getString("origin"),
            rs.getString("destination"),
            rs.getString("schedule"),
            rs.getString("status"),
            rs.getString("type"),
            rs.getDouble("price")
        );
    }

    private void setFlightPreparedStatement(PreparedStatement pstmt, Flight flight) throws SQLException {
        pstmt.setInt(1, flight.getflightNo());
        pstmt.setString(2, flight.getOrigin());
        pstmt.setString(3, flight.getdest());
        pstmt.setString(4, flight.getSchedule());
        pstmt.setString(5, flight.getStatus());
        pstmt.setString(6, flight.getType());
        pstmt.setDouble(7, flight.getPrice());
    }
}