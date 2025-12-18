
// // import javafx.event.ActionEvent;
// // import javafx.fxml.FXML;
// // import javafx.scene.control.Alert;
// // import javafx.scene.control.TextField;
// // import javafx.stage.Stage;
// // import javafx.fxml.FXMLLoader;
// // import javafx.scene.Scene;

// // public class SignUpController extends SharedController {
// //     @FXML private TextField nameField, cnicField, contactField, emailField,
// //             passportField, citizenshipField, visaTypeField, visaCountryField;

// //     private UserDAO userDAO = new UserDAO();

// //     @FXML
// //     private void handleSignUp() {
// //         User user = new User();
// //         user.setName(nameField.getText());
// //         user.setCnic(cnicField.getText());
// //         user.setContact(contactField.getText());
// //         user.setEmail(emailField.getText());
// //         user.setPassportNumber(passportField.getText());
// //         user.setCitizenship(citizenshipField.getText());
// //         user.setVisa(visaTypeField.getText() + ", " + visaCountryField.getText());
// //         user.setRole("customer");
// //         user.setEncryptedKey("mock_encrypted_" + System.currentTimeMillis());

// //         if (userDAO.signUp(user)) {
// //             new Alert(Alert.AlertType.INFORMATION,
// //                     "Signed up successfully! Your encrypted key is: " + user.getEncryptedKey())
// //                     .showAndWait();
// //             goBack(null); 
// //         } else {
// //             new Alert(Alert.AlertType.ERROR, "Sign up failed.").show();
// //         }
// //     }

// //     @FXML
// //     private void goBack(ActionEvent event) {
// //         try {
// //             Stage stage = (event != null)
// //                     ? getStageFromEvent(event)
// //                     : (Stage) nameField.getScene().getWindow();
// //             stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"))));
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
// import javafx.scene.control.Alert;
// import javafx.scene.control.TextField;
// import javafx.stage.Stage;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;

// public class SignUpController extends SharedController {
//     @FXML private TextField nameField, cnicField, contactField, emailField,
//             passportField, citizenshipField, visaTypeField, visaCountryField;

//     private UserDAO userDAO = new UserDAO();

//     @FXML
//     private void handleSignUp() {
//         User user = new User();
//         user.setName(nameField.getText());
//         user.setCnic(cnicField.getText());
//         user.setContact(contactField.getText());
//         user.setEmail(emailField.getText());
//         user.setPassportNumber(passportField.getText());
//         user.setCitizenship(citizenshipField.getText());
//         user.setVisa(visaTypeField.getText() + ", " + visaCountryField.getText());
//         user.setRole("customer");
//         user.setEncryptedKey("mock_encrypted_" + System.currentTimeMillis());

//         if (userDAO.signUp(user)) {
//             new Alert(Alert.AlertType.INFORMATION,
//                     "Signed up successfully! Your encrypted key is: " + user.getEncryptedKey())
//                     .showAndWait();
//             goBack(null); 
//         } else {
//             new Alert(Alert.AlertType.ERROR, "Sign up failed.").show();
//         }
//     }

//     @FXML
//     private void goBack(ActionEvent event) {
//         try {
//             Stage stage = (event != null)
//                     ? getStageFromEvent(event)
//                     : (Stage) nameField.getScene().getWindow();
//             double x = stage.getX();  // Preserve position
//             double y = stage.getY();
//             stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"))));
//             stage.setX(x);  // Restore position
//             stage.setY(y);
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }





import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class SignUpController extends SharedController {
    @FXML private TextField nameField, cnicField, contactField, emailField,
            passportField, citizenshipField, visaTypeField, visaCountryField;

    private UserDAO userDAO = new UserDAO();

    @FXML
    private void handleSignUp() {
        User user = new User();
        user.setName(nameField.getText());
        user.setCnic(cnicField.getText());
        user.setContact(contactField.getText());
        user.setEmail(emailField.getText());
        user.setPassportNumber(passportField.getText());
        user.setCitizenship(citizenshipField.getText());
        user.setVisa(visaTypeField.getText() + ", " + visaCountryField.getText());
        user.setRole("customer");
        user.setEncryptedKey("mock_encrypted_" + System.currentTimeMillis());

        Stage stage = (Stage) nameField.getScene().getWindow();  // Get stage for alert owner
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
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"))));
            stage.setX(x);  // Restore position
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}