import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookFlightController extends SharedController {
    @FXML private TableColumn<Flight, Double> priceCol;
    @FXML private TextField originField, destField, seatsField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Number> flightNoCol;
    @FXML private TableColumn<Flight, String> originCol, destCol, scheduleCol, statusCol;
    private ObservableList<Flight> allFlights = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        priceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()).asObject());
        flightNoCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()));
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDest()));
        scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        loadAllFlights();
    }

    private void loadAllFlights() {
        FlightDAO flightDAO = new FlightDAO();
        allFlights.addAll(flightDAO.getAllFlights());
    }

    @FXML
    private void searchFlights() {
        String origin = originField.getText().toLowerCase().trim();
        String dest = destField.getText().toLowerCase().trim();
        LocalDate date = datePicker.getValue();

        ObservableList<Flight> filtered = allFlights.stream()
            .filter(f -> (origin.isEmpty() || f.getOrigin().toLowerCase().contains(origin)) &&
                         (dest.isEmpty() || f.getDest().toLowerCase().contains(dest)) &&
                         (date == null || f.getSchedule().contains(date.toString())))
            .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));
            flightsTable.setItems(filtered);
    }

@FXML
private void confirmBooking() {
    Flight selected = flightsTable.getSelectionModel().getSelectedItem();
    if (selected == null) {
        new Alert(AlertType.WARNING, "Please select a flight.").show();
        return;
    }

    String seatsText = seatsField.getText().trim();
    if (seatsText.isEmpty()) {
        new Alert(AlertType.WARNING, "Enter number of seats.").show();
        return;
    }

    int seats;
    try {
        seats = Integer.parseInt(seatsText);
        if (seats <= 0) throw new NumberFormatException();
    } catch (NumberFormatException e) {
        new Alert(AlertType.WARNING, "Invalid number of seats.").show();
        return;
    }

    double totalCost = selected.getPrice() * seats;

    if (totalCost == 0) {
        new Alert(AlertType.WARNING, "Flight has no price set.").show();
        return;
    }

    // Visa check (existing)
    String visa = user.getVisa() == null ? "" : user.getVisa();
    if (selected.getType().equals("International") && 
        !visa.toLowerCase().contains(selected.getDest().toLowerCase())) {
        new Alert(AlertType.WARNING, 
            "You need a valid visa for " + selected.getDest() + ".").show();
        return;
    }

    // Wallet (salary) confirmation - only for customers
    if (!user.getRole().equals("customer")) {
        new Alert(AlertType.ERROR, "Only customers can book flights.").show();
        return;
    }

    Alert confirm = new Alert(AlertType.CONFIRMATION);
    confirm.setTitle("Confirm Booking");
    confirm.setHeaderText("Total: PKR " + String.format("%.2f", totalCost));
    confirm.setContentText(
        "Deduct PKR " + String.format("%.2f", totalCost) + 
        " from your wallet?\nCurrent balance: PKR " + 
        String.format("%.2f", user.getSalary()));

    Optional<ButtonType> result = confirm.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        if (user.getSalary() < totalCost) {
            new Alert(AlertType.ERROR, 
                "Insufficient balance! You have PKR " + 
                String.format("%.2f", user.getSalary())).show();
            return;
        }

        // Deduct from salary (wallet)
      user.setSalary(user.getSalary() - totalCost);
        UserDAO userDAO = new UserDAO();
      new UserDAO().updateUser(user);

        new Alert(AlertType.INFORMATION, 
            "Booking successful!\n" +
            seats + " seat(s) on flight " + selected.getflightNo() + 
            "\nDeducted: PKR " + String.format("%.2f", totalCost) + 
            "\nNew balance: PKR " + String.format("%.2f", user.getSalary()))
            .show();

        // Clear fields
        seatsField.clear();
        flightsTable.getSelectionModel().clearSelection();
    }
}

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.iniProfile();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}