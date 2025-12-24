import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CheckInController extends SharedController {
    @FXML private TextField flightNumberField, passengerCnicField;

    @FXML
    private void handleCheckIn() {
        String flightNum = flightNumberField.getText().trim();
        String cnic = passengerCnicField.getText().trim();
        if (flightNum.isEmpty() || cnic.isEmpty()) {
            new Alert(AlertType.WARNING, "Please fill in all fields.").show();
            return;
        }
        // Mock check-in logic
        new Alert(AlertType.INFORMATION, "Passenger with CNIC " + cnic + " checked in for flight " + flightNum + " (mock).").show();
        flightNumberField.clear();
        passengerCnicField.clear();
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