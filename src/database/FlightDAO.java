import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class FlightDAO {
    private static List<Flight> flights = new ArrayList<>();
    
    static {
        // Initialize with some default flights
        flights.add(new Flight(101, "LAX", "JFK", "2025-12-20 08:00", "On Time", "Domestic"));
        flights.add(new Flight(102, "JFK", "LAX", "2025-12-21 10:00", "Delayed", "Domestic"));
        flights.add(new Flight(103, "LAX", "LHR", "2025-12-22 14:00", "On Time", "International"));
        flights.add(new Flight(104, "SFO", "CDG", "2025-12-23 16:00", "On Time", "International"));
    }
    
    public ObservableList<Flight> getAllFlights() {
        return FXCollections.observableArrayList(flights);
    }
    
    public void addFlight(Flight flight) {
        flights.add(flight);
    }
    
    public void updateFlight(Flight flight) {
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getflightNo() == flight.getflightNo()) {
                flights.set(i, flight);
                return;
            }
        }
    }
    
    public void deleteFlight(int flightNo) {
        flights.removeIf(f -> f.getflightNo() == flightNo);
    }
    
    public Flight getFlightByNumber(int flightNo) {
        return flights.stream()
            .filter(f -> f.getflightNo() == flightNo)
            .findFirst()
            .orElse(null);
    }
    
    public boolean flightNoExists(int flightNo) {
        return flights.stream().anyMatch(f -> f.getflightNo() == flightNo);
    }
}

