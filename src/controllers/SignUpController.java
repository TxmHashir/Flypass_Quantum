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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;

public class SignUpController extends SharedController {
    @FXML private TextField nameField, cnicField, contactField, emailField,
                           passportField, citizenshipField, visaCountryField, profileImgField,
                           countryField, cityField, postCodeField;
    @FXML private ComboBox<String> visaTypeField;
    @FXML private ImageView previewImgView;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        visaTypeField.setItems(FXCollections.observableArrayList(
                "Tourist", "Business", "Student", "Work", "Transit", "Medical"
        ));
    }

    @FXML
    private void handleSignUp() {
        String name = nameField.getText().trim();
        String cnic = cnicField.getText().trim();
        String contact = contactField.getText().trim();
        String email = emailField.getText().trim();
        String passport = passportField.getText().trim();
        String citizenship = citizenshipField.getText().trim();
        String visaType = visaTypeField.getValue();
        String visaCountry = visaCountryField.getText().trim();
        String profileImg = profileImgField.getText().trim();
        String country = countryField.getText().trim();
        String city = cityField.getText().trim();
        String postCode = postCodeField.getText().trim();

        if (name.isEmpty() || cnic.isEmpty() || email.isEmpty()) {
            new Alert(AlertType.ERROR, "Please fill in all required fields.").show();
            return;
        }

        String visa = (visaType != null && !visaCountry.isEmpty()) ? visaType + ", " + visaCountry : null;

        User newUser = new User();
        newUser.setName(name);
        newUser.setCnic(cnic);
        newUser.setEmail(email);
        newUser.setContact(contact);
        newUser.setPassportNumber(passport);
        newUser.setCitizenship(citizenship);
        newUser.setVisa(visa);
        newUser.setRole("customer"); // Default to customer; adjust if needed
        newUser.setencrypKey(generateEncrypKey());
        newUser.setprofImgPath(profileImg.isEmpty() ? "titleicon.png" : profileImg);
        newUser.setCountry(country);
        newUser.setCity(city);
        newUser.setpostCode(postCode);

        userDAO.addUser(newUser);

        // Write encrypKey to USB
        writeKeyToUsb(newUser.getencrypKey());

        new Alert(AlertType.INFORMATION, "Sign up successful! Your encryption key is saved on USB.").show();
        goBack(null);
    }

    private String generateEncrypKey() {
        return UUID.randomUUID().toString();
    }

    private void writeKeyToUsb(String key) {
        List<File> usbDrives = UsbKeyFetcher.getUsbDrives(System.getProperty("os.name").toLowerCase());
        if (!usbDrives.isEmpty()) {
            File usbDrive = usbDrives.get(0); // Use first USB
            File keyFile = new File(usbDrive, "encrypted_key.txt");
            try (PrintWriter writer = new PrintWriter(keyFile)) {
                writer.println(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void chooseImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            profileImgField.setText(file.getAbsolutePath());
            previewImgView.setImage(new Image(file.toURI().toString()));
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
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}