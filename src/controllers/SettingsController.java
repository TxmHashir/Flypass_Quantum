import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SettingsController extends SharedController {

    @FXML private Label contentTitle;
    @FXML private TextArea contentBody;

    @FXML
    private void initialize() {
        showHelp(null); // default view
    }

    @FXML
    public void showContact(ActionEvent event) {
        setContent("Contact",
                "Need assistance or have a question?\n\n" +
                "Email: support@airportapp.com\n" +
                "Phone: +1 (800) 555-0147\n" +
                "Hours: Mon–Fri, 9:00 AM – 6:00 PM");
    }

    @FXML
    public void showHelp(ActionEvent event) {
        setContent("Help",
                "Tips to get started:\n" +
                "• Upload your profile image from the Profile page.\n" +
                "• Use your USB key to log in securely.\n" +
                "• Manage bookings and duties from your dashboard.\n\n" +
                "Need more? Contact support anytime.");
    }

    @FXML
    public void showFaq(ActionEvent event) {
        setContent("FAQ",
                "Q: How do I change my profile image?\n" +
                "A: Go to Profile and use the Upload Image button.\n\n" +
                "Q: My USB key isn’t detected.\n" +
                "A: Reconnect the drive and ensure the key file exists.\n\n" +
                "Q: How do I switch accounts?\n" +
                "A: Sign out from Profile, then log in with another key.");
    }

    private void setContent(String title, String body) {
        contentTitle.setText(title);
        contentBody.setText(body);
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
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