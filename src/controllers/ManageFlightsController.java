import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class ManageFlightsController extends SharedController {
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Integer> flightNoCol;
    @FXML private TableColumn<Flight, String> originCol, destCol, scheduleCol, statusCol, typeCol;
    @FXML private TableColumn<Flight, Double> priceCol;

    private FlightDAO flightDAO = new FlightDAO();

    @FXML
    private void initialize() {
        flightNoCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getdest()));
        scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        typeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getType()));
        priceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()).asObject());
        loadFlights();
    }

    public void loadFlights() {
        flightsTable.setItems(flightDAO.getAllFlights());
    }

    @FXML
    private void addFlight() {
        Flight newFlight = showFlightDialog(null);
        if (newFlight != null) {
            flightDAO.addFlight(newFlight);
            loadFlights();
        }
    }

    @FXML
    private void editFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Flight edited = showFlightDialog(selected);
            if (edited != null) {
                flightDAO.updateFlight(edited);
                loadFlights();
            }
        } else {
            new Alert(AlertType.WARNING, "No flight selected.").show();
        }
    }

    @FXML
    private void deleteFlight() {
        Flight selected = flightsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirm = new Alert(AlertType.CONFIRMATION, "Delete flight " + selected.getflightNo() + "?");
            if (confirm.showAndWait().get() == ButtonType.OK) {
                flightDAO.deleteFlight(selected.getflightNo());
                loadFlights();
            }
        } else {
            new Alert(AlertType.WARNING, "No flight selected.").show();
        }
    }

    private Flight showFlightDialog(Flight existing) {
        Dialog<Flight> dialog = new Dialog<>();
        dialog.setTitle(existing == null ? "Add Flight" : "Edit Flight");
        dialog.setHeaderText(null);

        ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField flightNoField = new TextField();
        TextField originField = new TextField();
        TextField destField = new TextField();
        TextField scheduleField = new TextField();
        TextField statusField = new TextField();
        TextField typeField = new TextField();
        TextField priceField = new TextField();

        if (existing != null) {
            flightNoField.setText(String.valueOf(existing.getflightNo()));
            flightNoField.setDisable(true);
            originField.setText(existing.getOrigin());
            destField.setText(existing.getdest());
            scheduleField.setText(existing.getSchedule());
            statusField.setText(existing.getStatus());
            typeField.setText(existing.getType());
            priceField.setText(String.valueOf(existing.getPrice()));
        }

        grid.add(new Label("Flight #:"), 0, 0);
        grid.add(flightNoField, 1, 0);
        grid.add(new Label("Origin:"), 0, 1);
        grid.add(originField, 1, 1);
        grid.add(new Label("Destination:"), 0, 2);
        grid.add(destField, 1, 2);
        grid.add(new Label("Schedule (YYYY-MM-DD HH:mm):"), 0, 3);
        grid.add(scheduleField, 1, 3);
        grid.add(new Label("Status:"), 0, 4);
        grid.add(statusField, 1, 4);
        grid.add(new Label("Type:"), 0, 5);
        grid.add(typeField, 1, 5);
        grid.add(new Label("Price:"), 0, 6);
        grid.add(priceField, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int flightNo = Integer.parseInt(flightNoField.getText().trim());
                    String origin = originField.getText().trim();
                    String dest = destField.getText().trim();
                    String scheduleStr = scheduleField.getText().trim();
                    LocalDateTime.parse(scheduleStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    String status = statusField.getText().trim();
                    String type = typeField.getText().trim();
                    double price = Double.parseDouble(priceField.getText().trim());

                    if (price < 0) {
                        throw new IllegalArgumentException("Price must be non-negative.");
                    }

                    return new Flight(flightNo, origin, dest, scheduleStr, status, type, price);
                } catch (DateTimeParseException e) {
                    new Alert(AlertType.ERROR, "Invalid schedule format. Use YYYY-MM-DD HH:mm.").show();
                } catch (NumberFormatException e) {
                    new Alert(AlertType.ERROR, "Invalid number format for flight # or price.").show();
                } catch (Exception e) {
                    new Alert(AlertType.ERROR, "Invalid input: " + e.getMessage()).show();
                }
            }
            return null;
        });

        Optional<Flight> result = dialog.showAndWait();
        return result.orElse(null);
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