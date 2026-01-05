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
import javax.swing.filechooser.FileSystemView;  // Added for drive type

public class SignUpController extends SharedController {
    @FXML private TextField nameField, cnicField, contactField, emailField,
                           passportField, citizenshipField, visaCountryField, profileImgField,
                           countryField, cityField, postCodeField;
    @FXML private ComboBox<String> visaTypeField;
    @FXML private ImageView previewImgView;  // New field for preview

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        visaTypeField.setItems(FXCollections.observableArrayList(
                "Tourist", "Business", "Student", "Work", "Transit", "Medical"
        ));
    }

    @FXML
    private void handleSignUp() {
        // Validate fields (add more validation as needed)
        if (nameField.getText().isEmpty() || cnicField.getText().isEmpty()) {
            new Alert(AlertType.WARNING, "Please fill in all required fields.").show();
            return;
        }

        User newUser = new User();
        newUser.setName(nameField.getText());
        newUser.setCnic(cnicField.getText());
        newUser.setEmail(emailField.getText());
        newUser.setContact(contactField.getText());
        newUser.setPassportNumber(passportField.getText());
        newUser.setCitizenship(citizenshipField.getText());
        newUser.setCountry(countryField.getText());
        newUser.setCity(cityField.getText());
        newUser.setpostCode(postCodeField.getText());
        String visaType = visaTypeField.getValue();
        String visaCountry = visaCountryField.getText();
        if (visaType != null && !visaCountry.isEmpty()) {
            newUser.setVisa(visaType + ", " + visaCountry);
        }
        newUser.setRole("customer");  // Default role
        newUser.setprofImgPath(profileImgField.getText());

        // Generate encrypted key
        String rawKey = generateRawKey();  // e.g., UUID
        String encrypKey = EncryptionUtil.encryptSHA256(rawKey);
        newUser.setencrypKey(encrypKey);

        // Find USB drive and save key
        File usbDrive = findUsbDrive();
        if (usbDrive != null) {
            File keyFile = new File(usbDrive, "encrypted_key.txt");
            try (PrintWriter writer = new PrintWriter(keyFile)) {
                writer.println(encrypKey);
                System.out.println("Successfully saved key to: " + keyFile.getAbsolutePath());  // Added debug
                if (userDAO.signUp(newUser)) {
                    new Alert(AlertType.INFORMATION, "Sign up successful! Key saved to USB: " + usbDrive.getAbsolutePath()).show();
                    goBack(null);  // Return to login
                } else {
                    new Alert(AlertType.ERROR, "Sign up failed.").show();
                }
            } catch (IOException e) {
                System.err.println("Failed to save key: " + e.getMessage());  // Added debug
                new Alert(AlertType.ERROR, "Failed to save key to USB: " + e.getMessage()).show();
            }
        } else {
            new Alert(AlertType.WARNING, "Insert a removable USB drive to save your key.").show();
        }
    }

    private String generateRawKey() {
        return UUID.randomUUID().toString();  // Or more secure random
    }

    private File findUsbDrive() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            String systemDrive = System.getenv("SystemDrive");
            if (systemDrive == null) systemDrive = "C:";
            String systemPrefix = systemDrive.substring(0, 2) + "\\";
            
            for (char c = 'A'; c <= 'Z'; c++) {
                String path = c + ":\\";
                File root = new File(path);
                if (root.exists() && root.canRead() && !path.startsWith(systemPrefix) && isRemovableDrive(root)) {
                    System.out.println("Found removable USB drive: " + path + " (Type: " + getDriveType(root) + ")");
                    return root;  // Return first removable USB drive
                }
            }
        } else if (os.contains("mac")) {
            File volumesDir = new File("/Volumes/");
            if (volumesDir.exists() && volumesDir.isDirectory()) {
                for (File vol : volumesDir.listFiles()) {
                    if (vol.isDirectory() && !new File(vol, "System").exists()) {
                        return vol;  // First non-system volume
                    }
                }
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            String[] mountPoints = {"/media/", "/mnt/"};
            for (String mp : mountPoints) {
                File dir = new File(mp);
                if (dir.exists() && dir.isDirectory()) {
                    for (File vol : dir.listFiles()) {
                        if (vol.isDirectory()) {
                            return vol;  // First mounted volume
                        }
                    }
                }
            }
        }
        System.out.println("No removable USB drive found.");
        return null;
    }

    private static boolean isRemovableDrive(File drive) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desc = fsv.getSystemTypeDescription(drive);
        // Check for "Removable Disk" or variations
        return desc != null && (desc.toLowerCase().contains("removable") ||
                                desc.toLowerCase().contains("usb") ||
                                desc.toLowerCase().contains("flash"));
    }

    private static String getDriveType(File drive) {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desc = fsv.getSystemTypeDescription(drive);
        return desc != null ? desc : "Unknown";
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