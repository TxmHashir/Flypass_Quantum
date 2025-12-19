import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ForgetPasswordController extends SharedController {

    @FXML private TextField cnicField;
    @FXML private TextField emailField;
    @FXML private Label messageLabel;

    @FXML
    private void handleRecoverPassword() {
        String cnic = cnicField.getText().trim();
        String email = emailField.getText().trim();

        if(cnic.isEmpty() || email.isEmpty()) {
            messageLabel.setText("Please enter both CNIC and Email.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        messageLabel.setText("A recovery key has been sent to " + email + " (mock).");
        messageLabel.setStyle("-fx-text-fill: green;");
        cnicField.clear();
        emailField.clear();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"))));
            stage.setX(x);  // Restore position
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}