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
    @FXML private VBox customerPane, staffPane, pilotPane, adminPane, airHostessPane;
    @FXML private Label nameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaLabel, visaStatusLabel;
    @FXML private Label pilotNameLabel, pilotCnicLabel, pilotEmailLabel, pilotContactLabel, pilotPassportLabel, pilotCitizenshipLabel, pilotVisaLabel, pilotVisaStatusLabel, pilotRoleLabel;
    // Assuming similar labels for staff: add if needed
    @FXML private Label staffNameLabel, staffCnicLabel, staffEmailLabel, staffContactLabel, staffPassportLabel, staffCitizenshipLabel, staffVisaLabel, staffVisaStatusLabel, staffRoleLabel;
    @FXML private Label airHostessNameLabel, airHostessCnicLabel, airHostessEmailLabel, airHostessContactLabel, airHostessPassportLabel, airHostessCitizenshipLabel, airHostessVisaLabel, airHostessVisaStatusLabel, airHostessRoleLabel;

    public void initializeProfile() {
        hideAllPanes();
        String role = user.getRole().toLowerCase();
        switch (role) {
            case "customer":
                customerPane.setVisible(true);
                String visa = user.getVisa();
                boolean isActive = visa != null && !visa.trim().isEmpty();
                nameLabel.setText("Name: " + user.getName());
                cnicLabel.setText("CNIC: " + user.getCnic());
                emailLabel.setText("Email: " + user.getEmail());
                contactLabel.setText("Contact: " + (user.getContact() != null ? user.getContact() : "N/A"));
                passportLabel.setText("Passport: " + (user.getPassportNumber() != null ? user.getPassportNumber() : "N/A"));
                citizenshipLabel.setText("Citizenship: " + (user.getCitizenship() != null ? user.getCitizenship() : "N/A"));
                visaStatusLabel.setText("Visa Status: " + (isActive ? "Active" : "Non Active"));
                visaLabel.setText("Visa: " + (visa != null ? visa : "N/A"));
                break;
            case "pilot":
                pilotPane.setVisible(true);
                String visaPilot = user.getVisa();
                boolean isActivePilot = visaPilot != null && !visaPilot.trim().isEmpty();
                pilotNameLabel.setText("Name: " + user.getName());
                pilotCnicLabel.setText("CNIC: " + user.getCnic());
                pilotEmailLabel.setText("Email: " + user.getEmail());
                pilotContactLabel.setText("Contact: " + (user.getContact() != null ? user.getContact() : "N/A"));
                pilotPassportLabel.setText("Passport: " + (user.getPassportNumber() != null ? user.getPassportNumber() : "N/A"));
                pilotCitizenshipLabel.setText("Citizenship: " + (user.getCitizenship() != null ? user.getCitizenship() : "N/A"));
                pilotVisaStatusLabel.setText("Visa Status: " + (isActivePilot ? "Active" : "Non Active"));
                pilotVisaLabel.setText("Visa: " + (visaPilot != null ? visaPilot : "N/A"));
                pilotRoleLabel.setText("Role: Pilot");
                break;
            case "staff":
                staffPane.setVisible(true);
                String visaStaff = user.getVisa();
                boolean isActiveStaff = visaStaff != null && !visaStaff.trim().isEmpty();
                staffNameLabel.setText("Name: " + user.getName());
                staffCnicLabel.setText("CNIC: " + user.getCnic());
                staffEmailLabel.setText("Email: " + user.getEmail());
                staffContactLabel.setText("Contact: " + (user.getContact() != null ? user.getContact() : "N/A"));
                staffPassportLabel.setText("Passport: " + (user.getPassportNumber() != null ? user.getPassportNumber() : "N/A"));
                staffCitizenshipLabel.setText("Citizenship: " + (user.getCitizenship() != null ? user.getCitizenship() : "N/A"));
                staffVisaStatusLabel.setText("Visa Status: " + (isActiveStaff ? "Active" : "Non Active"));
                staffVisaLabel.setText("Visa: " + (visaStaff != null ? visaStaff : "N/A"));
                staffRoleLabel.setText("Role: Staff");
                break;
            case "admin":
                adminPane.setVisible(true);
                // Add admin specific if needed
                break;
            case "airhostess":
                airHostessPane.setVisible(true);
                airHostessNameLabel.setText("Name: " + user.getName());
                airHostessCnicLabel.setText("CNIC: " + user.getCnic());
                airHostessEmailLabel.setText("Email: " + user.getEmail());
                airHostessContactLabel.setText("Contact: " + (user.getContact() != null ? user.getContact() : "N/A"));
                airHostessPassportLabel.setText("Passport: " + (user.getPassportNumber() != null ? user.getPassportNumber() : "N/A"));
                airHostessCitizenshipLabel.setText("Citizenship: " + (user.getCitizenship() != null ? user.getCitizenship() : "N/A"));
                String airHostessVisa = user.getVisa();
                boolean airHostessIsActive = airHostessVisa != null && !airHostessVisa.trim().isEmpty();
                airHostessVisaStatusLabel.setText("Visa Status: " + (airHostessIsActive ? "Active" : "Non Active"));
                airHostessVisaLabel.setText("Visa: " + (airHostessVisa != null ? airHostessVisa : "N/A"));
                airHostessRoleLabel.setText("Role: Air Hostess");
        }
    }

    private void hideAllPanes() {
        customerPane.setVisible(false);
        staffPane.setVisible(false);
        pilotPane.setVisible(false);
        adminPane.setVisible(false);
        airHostessPane.setVisible(false);
    }

    @FXML
    private void bookFlight(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookFlight.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
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
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
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
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
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
    private void viewPilotDuty(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PilotDuty.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            PilotDutyController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkInPassenger(ActionEvent event) {
        loadScene(event, "/fxml/PilotDuty.fxml", "Pilot Duty");
    }

    @FXML
    private void viewAirHostessDuty(ActionEvent event) {
        loadScene(event, "/fxml/AirHostessDuty.fxml", "Air Hostess Duty");
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CheckIn.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            CheckInController controller = loader.getController();
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