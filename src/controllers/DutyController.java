import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DutyController extends SharedController {
    @FXML private TableView<Duty> dutyTable;
    @FXML private TableColumn<Duty, String> timeCol, locCol;
    @FXML private TableColumn<Duty, Integer> flightCol;

    @FXML
    private void initialize() {
        timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        locCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        flightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());
    }

    public void loadDuties() {
        dutyTable.setItems(FXCollections.observableArrayList(user.getAssignedDuties()));
    }

    @FXML
    private void reportIssue() {
        Alert alert = new Alert(AlertType.INFORMATION, "Issue reported successfully (mock).");
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