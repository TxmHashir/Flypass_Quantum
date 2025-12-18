

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



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ApplyVisaController extends SharedController {
    @FXML private TextField visaTypeField, countryField;

    @FXML
    private void applyVisa() {
        String visa = visaTypeField.getText() + ", " + countryField.getText();
        user.setVisa(visa); 
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Visa applied successfully!");
        Stage stage = (Stage) visaTypeField.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
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