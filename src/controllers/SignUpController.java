import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
public class SignUpController extends SharedController {
    @FXML private TextField nameField, cnicField, contactField, emailField,
    passportField, citizenshipField, visaCountryField;
    @FXML private ComboBox<String> visaTypeField;
    private UserDAO userDAO = new UserDAO();
    @FXML
    private void initialize() {
        visaTypeField.setItems(FXCollections.observableArrayList(
        "Tourist", "Business", "Student", "Work", "Transit", "Medical"
        ));
    }
    @FXML
    private void handleSignUp() {
        String visaType = visaTypeField.getValue();
        if (visaType == null) {
            Alert alert = new Alert(AlertType.WARNING, "Please select a visa type.");
            alert.show();
            return;
        }
        User user = new User();
        user.setName(nameField.getText());
        user.setCnic(cnicField.getText());
        user.setContact(contactField.getText());
        user.setEmail(emailField.getText());
        user.setPassportNumber(passportField.getText());
        user.setCitizenship(citizenshipField.getText());
        user.setVisa(visaType + ", " + visaCountryField.getText());
        user.setRole("customer");
        user.setEncryptedKey("mock_encrypted_" + System.currentTimeMillis());
        Stage stage = (Stage) nameField.getScene().getWindow();
        if (userDAO.signUp(user)) {
            Alert alert = new Alert(AlertType.INFORMATION,
            "Signed up successfully! Your encrypted key is: " + user.getEncryptedKey());
            alert.initOwner(stage);
            alert.showAndWait();
            goBack(null);
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Sign up failed.");
            alert.initOwner(stage);
            alert.show();
        }
    }
    @FXML
    private void goBack(ActionEvent event) {
        try {
            Stage stage = (event != null)
            ? getStageFromEvent(event)
            : (Stage) nameField.getScene().getWindow();
            double x = stage.getX();
            double y = stage.getY();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}