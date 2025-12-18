
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.layout.VBox;
// import javafx.scene.control.Label;
// import javafx.scene.control.Alert;
// import javafx.stage.Stage;

// public class ProfileController extends SharedController {

//     @FXML private VBox customerPane, staffPane, pilotPane, adminPane;
//     @FXML private Label nameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaLabel;

//     public void initializeProfile() {
//         hideAllPanes();

//         String role = user.getRole().toLowerCase();
//         switch (role) {
//             case "customer":
//                 customerPane.setVisible(true);
//                 nameLabel.setText("Name: " + user.getName());
//                 cnicLabel.setText("CNIC: " + user.getCnic());
//                 emailLabel.setText("Email: " + user.getEmail());
//                 contactLabel.setText("Contact: " + user.getContact());
//                 passportLabel.setText("Passport: " + user.getPassportNumber());
//                 citizenshipLabel.setText("Citizenship: " + user.getCitizenship());
//                 visaLabel.setText("Visa: " + user.getVisa());
//                 break;
//             case "staff":
//                 staffPane.setVisible(true);
//                 break;
//             case "pilot":
//                 pilotPane.setVisible(true);
//                 break;
//             case "admin":
//                 adminPane.setVisible(true);
//                 break;
//         }
//     }

//     private void hideAllPanes() {
//         customerPane.setVisible(false);
//         staffPane.setVisible(false);
//         pilotPane.setVisible(false);
//         adminPane.setVisible(false);
//     }

//     @FXML private void viewFlights(ActionEvent event) {
//         new Alert(Alert.AlertType.INFORMATION, "Flights view not implemented yet.").show();
//     }
    
//     @FXML private void bookFlight(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookFlight.fxml"));
//             Stage stage = getStageFromEvent(event);
//             stage.setScene(new Scene(loader.load()));
//             BookFlightController controller = loader.getController();
//             controller.setUser(user);
//             stage.setWidth(700);
//             stage.setHeight(550);
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
    
//     @FXML private void applyVisa(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplyVisa.fxml"));
//             Stage stage = getStageFromEvent(event);
//             stage.setScene(new Scene(loader.load()));
//             ApplyVisaController controller = loader.getController();
//             controller.setUser(user);
//             stage.setWidth(700);
//             stage.setHeight(550);
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
    
//     @FXML private void viewDuty(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DutySchedule.fxml"));
//             Stage stage = getStageFromEvent(event);
//             stage.setScene(new Scene(loader.load()));
//             DutyController controller = loader.getController();
//             controller.setUser(user);
//             stage.setWidth(700);
//             stage.setHeight(550);
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
    
//     @FXML private void manageUsers(ActionEvent event) {
//         new Alert(Alert.AlertType.INFORMATION, "User management not implemented yet.").show();
//     }
    
//     @FXML private void manageFlights(ActionEvent event) {
//         new Alert(Alert.AlertType.INFORMATION, "Flight management not implemented yet.").show();
//     }
// }





import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ProfileController extends SharedController {

    @FXML private VBox customerPane, staffPane, pilotPane, adminPane;
    @FXML private Label nameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaLabel;

    public void initializeProfile() {
        hideAllPanes();

        String role = user.getRole().toLowerCase();
        switch (role) {
            case "customer":
                customerPane.setVisible(true);
                nameLabel.setText("Name: " + user.getName());
                cnicLabel.setText("CNIC: " + user.getCnic());
                emailLabel.setText("Email: " + user.getEmail());
                contactLabel.setText("Contact: " + user.getContact());
                passportLabel.setText("Passport: " + user.getPassportNumber());
                citizenshipLabel.setText("Citizenship: " + user.getCitizenship());
                visaLabel.setText("Visa: " + user.getVisa());
                break;
            case "staff":
                staffPane.setVisible(true);
                break;
            case "pilot":
                pilotPane.setVisible(true);
                break;
            case "admin":
                adminPane.setVisible(true);
                break;
        }
    }

    private void hideAllPanes() {
        customerPane.setVisible(false);
        staffPane.setVisible(false);
        pilotPane.setVisible(false);
        adminPane.setVisible(false);
    }

    @FXML private void viewFlights(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Flights view not implemented yet.").show();
    }
    
    @FXML private void bookFlight(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookFlight.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
            stage.setY(y);
            BookFlightController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML private void applyVisa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplyVisa.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
            stage.setY(y);
            ApplyVisaController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML private void viewDuty(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DutySchedule.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
            stage.setY(y);
            DutyController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML private void manageUsers(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "User management not implemented yet.").show();
    }
    
    @FXML private void manageFlights(ActionEvent event) {
        new Alert(Alert.AlertType.INFORMATION, "Flight management not implemented yet.").show();
    }
}