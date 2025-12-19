
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ApplyVisaController extends SharedController {

    // These IDs must exactly match the fx:id in your FXML
    @FXML private TextField nameField;
    @FXML private TextField cnicField;
    @FXML private TextField passportField;
    @FXML private TextField bankNameField;
    @FXML private TextField bankAccountField;
    @FXML private TextField countryField;
    @FXML private ComboBox<String> visaTypeComboBox;

    @FXML
    private void initialize() {
        // Populating the ComboBox as defined in your logic
        visaTypeComboBox.setItems(FXCollections.observableArrayList(
            "Tourist", "Business", "Student", "Work", "Transit", "Medical"
        ));
    }

    @FXML
    private void applyVisa() {
        String visaType = visaTypeComboBox.getValue();
        String country = countryField.getText();

        // Validation
        if (visaType == null || country.isEmpty() || nameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.");
            alert.initOwner((Stage) countryField.getScene().getWindow());
            alert.show();
            return;
        }

        // Processing logic
        user.setVisa(visaType + " to " + country);
        
        Alert success = new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully!");
        success.initOwner((Stage) countryField.getScene().getWindow());
        success.show();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            
            // Navigate back to Profile
            stage.setScene(new Scene(loader.load()));
            
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
            
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}