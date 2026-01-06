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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.collections.FXCollections;

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

    public void loadFlights() {
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
        if (selected != null) {
            showFlightDialog(selected);
        }
    }

    @FXML
    private void deleteFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(AlertType.CONFIRMATION, "Delete flight " + selected.getflightNo() + "?");
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    flightDAO.deleteFlight(selected.getflightNo());
                    loadFlights();
                }
            });
        }
    }

    private void showFlightDialog(Flight flight) {
        Dialog<Flight> dialog = new Dialog<>();
        dialog.setTitle(flight == null ? "Add Flight" : "Edit Flight");
        ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField flightNoField = new TextField();
        TextField originField = new TextField();
        TextField destField = new TextField();
        TextField scheduleField = new TextField();
        TextField statusField = new TextField();
        ComboBox<String> typeCombo = new ComboBox<>(FXCollections.observableArrayList("Domestic", "International"));

        grid.add(new Label("Flight No:"), 0, 0);
        grid.add(flightNoField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("Destination:"), 0, 2);
        grid.add(destField, 1, 2);
        grid.add(new Label("Schedule:"), 0, 3);
        grid.add(scheduleField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Type:"), 0, 5);
        grid.add(typeCombo, 1, 5);

        if (flight != null) {
            flightNoField.setText(String.valueOf(flight.getflightNo()));
            flightNoField.setDisable(true);
            originField.setText(flight.getOrigin());
            destField.setText(flight.getdest());
            scheduleField.setText(flight.getSchedule());
            statusField.setText(flight.getStatus());
            typeCombo.setValue(flight.getType());
        }

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int flightNo = Integer.parseInt(flightNoField.getText());
                    String origin = originField.getText();
                    String dest = destField.getText();
                    String schedule = scheduleField.getText();
                    String status = statusField.getText();
                    String type = typeCombo.getValue();
                    return new Flight(flightNo, origin, dest, schedule, status, type);
                } catch (Exception e) {
                    new Alert(AlertType.ERROR, "Invalid input.").show();
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
                flightResult.setflightNo(flight.getflightNo());
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