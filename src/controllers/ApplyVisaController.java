import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class ApplyVisaController extends SharedController {
    @FXML private TextField nameField;
    @FXML private TextField cnicField;
    @FXML private TextField passportField;
    @FXML private TextField bankNameField;
    @FXML private TextField bankAccField;
    @FXML private TextField countryField;
    @FXML private ComboBox<String> visaTypeComboBox;
    @FXML
    private void initialize() {
        visaTypeComboBox.setItems(FXCollections.observableArrayList(
        "Tourist", "Business", "Student", "Work", "Transit", "Medical"
        ));
    }
    public void prefillFields() {
        if (user != null) {
            nameField.setText(user.getName());
            cnicField.setText(user.getCnic());
            passportField.setText(user.getPassportNumber());
            bankNameField.setText(user.getBankName());
            bankAccField.setText(user.getbankAcc());
        }
    }
    @FXML
    private void applyVisa() {
        String visaType = visaTypeComboBox.getValue();
        String country = countryField.getText().trim();
        if (visaType == null || country.isEmpty()) {
            new Alert(AlertType.WARNING, "Select visa type and enter country.").show();
            return;
        }
        user.setName(nameField.getText());
        user.setCnic(cnicField.getText());
        user.setPassportNumber(passportField.getText());
        user.setBankName(bankNameField.getText());
        user.setbankAcc(bankAccField.getText());
        user.setVisa(visaType + ", " + country);
        // Save
        UserDAO userDAO = new UserDAO();
        userDAO.updateUser(user);
        Alert success = new Alert(AlertType.INFORMATION, "Visa applied successfully!");
        success.initOwner((Stage) countryField.getScene().getWindow());
        success.show();
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