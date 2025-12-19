import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class ViewVisaController extends SharedController {

    @FXML private Label visaTypeLabel, visaCountryLabel, visaStatusLabel;

    public void initializeVisaDetails() {
        String visa = user.getVisa();
        if (visa != null && !visa.isEmpty()) {
            String[] parts = visa.split(", ");
            if (parts.length == 2) {
                visaTypeLabel.setText("Visa Type: " + parts[0]);
                visaCountryLabel.setText("Country: " + parts[1]);
                visaStatusLabel.setText("Status: Active");
                visaStatusLabel.setStyle("-fx-text-fill: green; -fx-effect: dropshadow(gaussian, green, 10, 0.5, 0, 0);");
            }
        } else {
            visaTypeLabel.setText("Visa Type: N/A");
            visaCountryLabel.setText("Country: N/A");
            visaStatusLabel.setText("Status: Inactive");
            visaStatusLabel.setStyle("-fx-text-fill: red; -fx-effect: dropshadow(gaussian, red, 10, 0.5, 0, 0);");
        }
    }

    @FXML
    private void printVisa() {
        new Alert(Alert.AlertType.INFORMATION, "Visa details printed to PDF (mock).").show();
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            stage.setScene(new Scene(loader.load()));
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
            // stage.setFullScreen(true);
            stage.setMaximized(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}