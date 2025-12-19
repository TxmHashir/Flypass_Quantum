import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class ProfileController extends SharedController {
    @FXML private VBox customerPane, staffPane, pilotPane, adminPane;
    @FXML private Label nameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaLabel, visaStatusLabel;
    public void initializeProfile() {
        hideAllPanes();
        String role = user.getRole().toLowerCase();
        switch (role) {
        case "customer":
            customerPane.setVisible(true);
            nameLabel.setText("Name: " + user.getName());
            cnicLabel.setText("CNIC: " + user.getCnic());
            emailLabel.setText("Email: " + user.getEmail());
            contactLabel.setText("Contact: " + (user.getContact() != null ? user.getContact() : "N/A"));
            passportLabel.setText("Passport: " + (user.getPassportNumber() != null ? user.getPassportNumber() : "N/A"));
            citizenshipLabel.setText("Citizenship: " + (user.getCitizenship() != null ? user.getCitizenship() : "N/A"));
            String visa = user.getVisa();
            boolean isActive = visa != null && !visa.trim().isEmpty();
            visaStatusLabel.setText("Visa Status: " + (isActive ? "Active" : "Non Active"));
            visaLabel.setText("Visa: " + (visa != null ? visa : "N/A"));
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
    @FXML
    private void bookFlight(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookFlight.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            BookFlightController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void applyVisa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ApplyVisa.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ApplyVisaController controller = loader.getController();
            controller.setUser(user);
            controller.prefillFields();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void viewDuty(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DutySchedule.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            DutyController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void manageUsers(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION, "User management not implemented yet.");
        Stage stage = getStageFromEvent(event);
        alert.initOwner(stage);
        alert.show();
    }
    @FXML
    private void manageFlights(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION, "Flight management not implemented yet.");
        Stage stage = getStageFromEvent(event);
        alert.initOwner(stage);
        alert.show();
    }
    @FXML
    private void viewVisa(ActionEvent event) {
        String visa = user.getVisa();
        if (visa == null || visa.trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING, "You have not applied for a visa yet. Please apply first.");
            alert.initOwner(getStageFromEvent(event));
            alert.show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewVisa.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ViewVisaController controller = loader.getController();
            controller.setUser(user);
            controller.initializeVisaDetails();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}