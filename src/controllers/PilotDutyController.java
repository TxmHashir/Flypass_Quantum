import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

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
    }

    public void loadDuties() {
        // Loads duties assigned specifically to this pilot
        dutyTable.setItems(FXCollections.observableArrayList(user.getAssignedFlights()));
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
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}