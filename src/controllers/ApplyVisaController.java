// // // import app.models.User;
// // import javafx.event.ActionEvent;
// // import javafx.fxml.FXML;
// // import javafx.fxml.FXMLLoader;
// // import javafx.scene.Scene;
// // import javafx.scene.control.TextField;
// // import javafx.scene.control.Alert;
// // import javafx.stage.Stage;

// // public class ApplyVisaController extends SharedController {
// //     @FXML private TextField visaTypeField, countryField;

// //     @FXML
// //     private void applyVisa() {
// //         String visa = visaTypeField.getText() + ", " + countryField.getText();
// //         user.setVisa(visa); 
// //         new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully!").show();
// //     }

// //     @FXML
// //     private void goBack(ActionEvent event) {
// //         try {
// //             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
// //             Stage stage = getStageFromEvent(event);
// //             stage.setScene(new Scene(loader.load()));
// //             ProfileController controller = loader.getController();
// //             controller.setUser(user);
// //             controller.initializeProfile();
// //             stage.setWidth(700);
// //             stage.setHeight(550);
// //             stage.show();
// //         } catch (Exception e) {
// //             e.printStackTrace();
// //         }
// //     }
// // }


// // import app.models.User;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Alert;
// import javafx.stage.Stage;

// public class ApplyVisaController extends SharedController {
//     @FXML private TextField visaTypeField, countryField;

//     @FXML
//     private void applyVisa() {
//         String visa = visaTypeField.getText() + ", " + countryField.getText();
//         user.setVisa(visa); 
//         new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully!").show();
//     }

//     @FXML
//     private void goBack(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
//             Stage stage = getStageFromEvent(event);
//             double x = stage.getX();  // Preserve position
//             double y = stage.getY();
//             stage.setScene(new Scene(loader.load()));
//             stage.setX(x);  // Restore position
//             stage.setY(y);
//             ProfileController controller = loader.getController();
//             controller.setUser(user);
//             controller.initializeProfile();
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.control.TextField;
// import javafx.scene.control.Alert;
// import javafx.stage.Stage;

// public class ApplyVisaController extends SharedController {
//     @FXML private TextField visaTypeField, countryField;

//     @FXML
//     private void applyVisa() {
//         String visa = visaTypeField.getText() + ", " + countryField.getText();
//         user.setVisa(visa); 
//         Alert alert = new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully!");
//         Stage stage = (Stage) visaTypeField.getScene().getWindow();
//         alert.initOwner(stage);
//         alert.show();
//     }

//     @FXML
//     private void goBack(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
//             Stage stage = getStageFromEvent(event);
//             double x = stage.getX();  // Preserve position
//             double y = stage.getY();
//             stage.setScene(new Scene(loader.load()));
//             stage.setX(x);  // Restore position
//             stage.setY(y);
//             ProfileController controller = loader.getController();
//             controller.setUser(user);
//             controller.initializeProfile();
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.scene.control.Alert;
// import javafx.scene.control.ComboBox;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.collections.FXCollections;
// public class ApplyVisaController extends SharedController {
// @FXML private TextField nameField, cnicField, passportField, bankNameField, bankAccountField, countryField;
// @FXML private ComboBox<String> visaTypeComboBox;
// @FXML
// private void initialize() {
// visaTypeComboBox.setItems(FXCollections.observableArrayList(
// "Tourist", "Business", "Student", "Work", "Transit", "Medical"
// ));
// }
// @FXML
// private void applyVisa() {
// String visaType = visaTypeComboBox.getValue();
// String country = countryField.getText();
// if (visaType == null || country.isEmpty()) {
// new Alert(Alert.AlertType.ERROR, "Please select visa type and enter country.").show();
// return;
// }
// // Mock applying visa - in real app, validate all fields and process
// user.setVisa(visaType + ", " + country);
// new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully! (Mock)").show();
// }
// @FXML
// private void goBack(ActionEvent event) {
// try {
// FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
// Stage stage = getStageFromEvent(event);
// stage.setScene(new Scene(loader.load()));
// ProfileController controller = loader.getController();
// controller.setUser(user);
// controller.initializeProfile();
// // Do not set width/height to allow maximized state to persist
// // stage.setFullScreen(true);
// stage.setMaximized(true);
// stage.show();
// } catch (Exception e) {
// e.printStackTrace();
// }
// }
// }

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