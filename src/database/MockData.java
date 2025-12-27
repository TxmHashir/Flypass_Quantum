import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MockData {
    private static List<Flight> allFlights = new ArrayList<>();
    private static List<Duty> allDuties = new ArrayList<>();

    static {
        allFlights.add(new Flight(101, "LAX", "JFK", "2025-12-20 08:00", "On Time", "Domestic"));
        allFlights.add(new Flight(102, "JFK", "LAX", "2025-12-21 10:00", "Delayed", "Domestic"));
        allFlights.add(new Flight(103, "SFO", "ORD", "2025-12-22 12:00", "On Time", "Domestic"));
        
        allDuties.add(new Duty("08:00-12:00", "LAX Terminal 1", 101));
        allDuties.add(new Duty("12:00-16:00", "NYC Terminal 2", 102));
        allDuties.add(new Duty("16:00-20:00", "SFO Terminal 3", 103));
    }

    public static ObservableList<Flight> getAllFlights() {
        return FXCollections.observableArrayList(allFlights);
    }

    public static ObservableList<Duty> getAllDuties() {
        return FXCollections.observableArrayList(allDuties);
    }
}