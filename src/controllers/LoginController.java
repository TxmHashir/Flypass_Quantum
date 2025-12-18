 // <--- MAKE SURE THIS MATCHES YOUR ACTUAL PACKAGE

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class LoginController extends SharedController {

    @FXML private PasswordField keyField;
    private UserDAO userDAO = new UserDAO();

    // 1. Handle "Enter" key press
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    // 2. Handle Login with Liquid Glass Alert
  @FXML
    private void handleLogin() {
        String encryptedKey = keyField.getText().trim();
        User user = userDAO.getUserByEncryptedKey(encryptedKey);
        Stage stage = (Stage) keyField.getScene().getWindow(); 

        if (user != null) {
            openProfile(user);
        } else {
            // --- UPDATED LIQUID GLASS ALERT ---
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(stage);
            alert.initStyle(StageStyle.TRANSPARENT);
            alert.setHeaderText("Access Denied");
            alert.setContentText("Invalid encrypted key. Please try again.");
            
            // REMOVE the default icon to stop it from looking messy
            alert.setGraphic(null); 

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("/css/glass.css").toExternalForm());
            dialogPane.getStyleClass().add("glass-alert");

            Scene alertScene = dialogPane.getScene();
            alertScene.setFill(Color.TRANSPARENT);

            // Smooth Materialization
            dialogPane.setOpacity(0);
            FadeTransition fade = new FadeTransition(Duration.millis(300), dialogPane);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

            // USE showAndWait to block interaction with the background login
            alert.showAndWait(); 
        }
    }
    // 3. Navigation to Profile
    private void openProfile(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = (Stage) keyField.getScene().getWindow();
            double x = stage.getX();
            double y = stage.getY();
            
            stage.setScene(new Scene(loader.load()));
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

    // 4. Navigation to Sign Up (This fixes your current error!)
    @FXML 
    private void goToSignUp(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/SignUp.fxml"))));
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    // 5. Navigation to Forget Password
    @FXML 
    private void goToForgetPassword(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/ForgetPassword.fxml"))));
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}