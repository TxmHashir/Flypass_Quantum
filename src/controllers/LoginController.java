import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
public class LoginController extends SharedController {
    @FXML private PasswordField keyField;
    private UserDAO userDAO = new UserDAO();
    @FXML
    private void handleKeyPress(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            handleLogin();
        }
    }
    @FXML
    private void handleLogin() {
        String encryptedKey = keyField.getText().trim();
        User user = userDAO.getUserByEncryptedKey(encryptedKey);
        Stage stage = (Stage) keyField.getScene().getWindow();
        if (user != null) {
            openProfile(user);
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Invalid encrypted key.");
            alert.initOwner(stage);
            alert.show();
        }
    }
    private void openProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = (Stage) keyField.getScene().getWindow();
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML private void goToSignUp(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }
    @FXML private void goToForgetPassword(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ForgetPassword.fxml"));
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }
}