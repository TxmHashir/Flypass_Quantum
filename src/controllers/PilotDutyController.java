import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PilotDutyController extends SharedController {
    @FXML private TableView<Flight> dutyTable;
    @FXML private TableColumn<Flight, Integer> flightCol;
    @FXML private TableColumn<Flight, String> originCol, destinationCol, timeCol, statusCol;

    @FXML
    private void initialize() {
        flightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
        timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
        dutyTable.setItems(getMockFlights());
    }

    private ObservableList<Flight> getMockFlights() {
        ObservableList<Flight> list = FXCollections.observableArrayList();
        list.add(new Flight(101, "LAX", "JFK", "2025-12-20 08:00", "On Time", "Domestic"));
        list.add(new Flight(102, "JFK", "LAX", "2025-12-21 10:00", "Delayed", "Domestic"));
        return list;
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            Scene newScene = new Scene(loader.load());
            stage.setScene(newScene);
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }
}