import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class BookFlightController extends SharedController {
    @FXML private TextField originField, destinationField, seatsField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Number> flightNumberCol;
    @FXML private TableColumn<Flight, String> originCol, destinationCol, scheduleCol, statusCol;
    private ObservableList<Flight> allFlights = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        flightNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()));
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
        scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        // Load mock flights
        allFlights.addAll(getMockFlights());
        flightsTable.setItems(allFlights);
    }

    private ObservableList<Flight> getMockFlights() {
        return FXCollections.observableArrayList(
                new Flight(101, "LAX", "JFK", "2025-12-20 08:00", "On Time", "Domestic"),
                new Flight(102, "JFK", "LAX", "2025-12-21 10:00", "Delayed", "Domestic"),
                new Flight(103, "LAX", "LHR", "2025-12-22 14:00", "On Time", "International"),
                new Flight(104, "SFO", "CDG", "2025-12-23 16:00", "On Time", "International")
        );
    }

    @FXML
    private void searchFlights() {
        String origin = originField.getText().toLowerCase().trim();
        String destination = destinationField.getText().toLowerCase().trim();
        LocalDate date = datePicker.getValue();

        ObservableList<Flight> filtered = allFlights.stream()
                .filter(f -> (origin.isEmpty() || f.getOrigin().toLowerCase().contains(origin))
                        && (destination.isEmpty() || f.getDestination().toLowerCase().contains(destination))
                        && (date == null || f.getSchedule().contains(date.toString())))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        flightsTable.setItems(filtered);
    }

    @FXML
    private void confirmBooking() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(AlertType.WARNING, "Please select a flight to book.").show();
            return;
        }
        String seatsStr = seatsField.getText().trim();
        if (seatsStr.isEmpty()) {
            new Alert(AlertType.WARNING, "Please enter the number of seats.").show();
            return;
        }
        int seats;
        try {
            seats = Integer.parseInt(seatsStr);
            if (seats <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            new Alert(AlertType.WARNING, "Invalid number of seats.").show();
            return;
        }

        // Visa check for international flights
        String type = selected.getType();
        if ("International".equals(type)) {
            String visa = user.getVisa();
            if (visa == null || !visa.toLowerCase().contains(selected.getDestination().toLowerCase())) {
                new Alert(AlertType.WARNING, "You need a valid visa for " + selected.getDestination() + ".").show();
                return;
            }
        }

        // Mock booking logic
        Alert alert = new Alert(AlertType.INFORMATION, "Payment successful! Booked " + seats + " seats on flight " + selected.getFlightNumber() + ".");
        alert.initOwner(flightsTable.getScene().getWindow());
        alert.show();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets()); // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}