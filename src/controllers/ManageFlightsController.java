import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.util.Optional;

public class ManageFlightsController extends SharedController {
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Number> flightNoCol;
    @FXML private TableColumn<Flight, String> originCol, destCol, scheduleCol, statusCol, typeCol;
    
    private FlightDAO flightDAO = new FlightDAO();
    private ObservableList<Flight> flightsList;

    @FXML
    private void initialize() {
        flightNoCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()));
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getdest()));
        scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        typeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        
        loadFlights();
    }

    private void loadFlights() {
        flightsList = flightDAO.getAllFlights();
        flightsTable.setItems(flightsList);
    }

    @FXML
    private void addFlight() {
        showFlightDialog(null);
    }

    @FXML
    private void editFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(AlertType.WARNING, "Please select a flight to edit.").show();
            return;
        }
        showFlightDialog(selected);
    }

    @FXML
    private void deleteFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(AlertType.WARNING, "Please select a flight to delete.").show();
            return;
        }
        
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Delete Flight");
        confirm.setHeaderText("Delete Flight " + selected.getflightNo());
        confirm.setContentText("Are you sure you want to delete this flight?");
        
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            flightDAO.deleteFlight(selected.getflightNo());
            loadFlights();
            new Alert(AlertType.INFORMATION, "Flight deleted successfully.").show();
        }
    }

    private void showFlightDialog(Flight flight) {
        Dialog<Flight> dialog = new Dialog<>();
        dialog.setTitle(flight == null ? "Add Flight" : "Edit Flight");
        dialog.setHeaderText(flight == null ? "Add New Flight" : "Edit Flight Information");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField flightNoField = new TextField();
        flightNoField.setPromptText("Flight Number");
        TextField originField = new TextField();
        originField.setPromptText("Origin (e.g., LAX)");
        TextField destField = new TextField();
        destField.setPromptText("dest (e.g., JFK)");
        TextField scheduleField = new TextField();
        scheduleField.setPromptText("Schedule (e.g., 2025-12-20 08:00)");
        ComboBox<String> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("On Time", "Delayed", "Cancelled", "Boarding");
        statusCombo.setValue("On Time");
        ComboBox<String> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Domestic", "International");
        typeCombo.setValue("Domestic");

        if (flight != null) {
            flightNoField.setText(String.valueOf(flight.getflightNo()));
            flightNoField.setEditable(false); 
            originField.setText(flight.getOrigin());
            destField.setText(flight.getdest());
            scheduleField.setText(flight.getSchedule());
            statusCombo.setValue(flight.getStatus());
            typeCombo.setValue(flight.getType());
        }

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        grid.add(new Label("Flight Number:"), 0, 0);
        grid.add(flightNoField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("dest:"), 0, 2);
        grid.add(destField, 1, 2);
        grid.add(new Label("Schedule:"), 0, 3);
        grid.add(scheduleField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusCombo, 1, 4);
        grid.add(new Label("Type:"), 0, 5);
        grid.add(typeCombo, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int flightNo = Integer.parseInt(flightNoField.getText().trim());
                    String origin = originField.getText().trim();
                    String dest = destField.getText().trim();
                    String schedule = scheduleField.getText().trim();
                    String status = statusCombo.getValue();
                    String type = typeCombo.getValue();

                    if (origin.isEmpty() || dest.isEmpty() || schedule.isEmpty()) {
                        new Alert(AlertType.WARNING, "Please fill in all fields.").show();
                        return null;
                    }

                    if (flight == null) {
                        if (flightDAO.flightNoExists(flightNo)) {
                            new Alert(AlertType.WARNING, "Flight number already exists.").show();
                            return null;
                        }
                        return new Flight(flightNo, origin, dest, schedule, status, type);
                    } else {
                        Flight edited = new Flight(flightNo, origin, dest, schedule, status, type);
                        return edited;
                    }
                } catch (NumberFormatException e) {
                    new Alert(AlertType.WARNING, "Please enter a valid flight number.").show();
                    return null;
                }
            }
            return null;
        });

        Optional<Flight> result = dialog.showAndWait();
        result.ifPresent(flightResult -> {
            if (flight == null) {
                flightDAO.addFlight(flightResult);
                new Alert(AlertType.INFORMATION, "Flight added successfully.").show();
            } else {
                flightDAO.updateFlight(flightResult);
                new Alert(AlertType.INFORMATION, "Flight updated successfully.").show();
            }
            loadFlights();
        });
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
            controller.initializeProfile();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

