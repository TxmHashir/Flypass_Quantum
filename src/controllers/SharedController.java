import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
public class SharedController {
protected User user;
protected User loggedInUser;
public void setUser(User user) {
this.user = user;
}
public void setLoggedInUser(User loggedInUser) {
    this.loggedInUser = loggedInUser;
}
public void signOut(ActionEvent event) {
// Check if USB is still inserted - use the new search method
String key = UsbKeyFetcher.searchAllTxtFiles();
if (key == null) {
key = UsbKeyFetcher.fetchEncryptionKeyFromUsb("encrypted_key.txt");
}
if (key != null) {
Alert alert = new Alert(AlertType.WARNING, "First remove your card.");
alert.initOwner(getStageFromEvent(event));
alert.show();
return;  // Prevent sign out
}
try {
Stage stage = getStageFromEvent(event);
double x = stage.getX();
double y = stage.getY();
FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));  // Use loader for consistency
Scene newScene = new Scene(loader.load());
newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
stage.setScene(newScene);
stage.setX(x);
stage.setY(y);
stage.show();
LoginController controller = loader.getController();
controller.setJustSignedOut(true);
controller.startUsbPolling();  // Restart USB polling when logged out
} catch (IOException e) {
e.printStackTrace();
}
}
public void openSettings(ActionEvent event) {
try {
FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
Stage stage = getStageFromEvent(event);
double x = stage.getX();
double y = stage.getY();
Scene newScene = new Scene(loader.load());
newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
stage.setScene(newScene);
stage.setX(x);
stage.setY(y);
SettingsController controller = loader.getController();
controller.setUser(user);
stage.show();
} catch (IOException e) {
e.printStackTrace();
}
}
protected Stage getStageFromEvent(ActionEvent event) {
return (Stage)((Node)event.getSource()).getScene().getWindow();
}
}