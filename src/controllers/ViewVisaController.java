import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewVisaController extends SharedController {

    @FXML private Label nameLabel, cnicLabel, emailLabel, visaTypeLabel, visaCountryLabel;
    @FXML private VBox visaDetailsPane;

    @FXML
    private void initialize() {
        if (user != null) {
            nameLabel.setText("Name: " + user.getName());
            cnicLabel.setText("CNIC: " + user.getCnic());
            emailLabel.setText("Email: " + user.getEmail());
            String visa = user.getVisa();
            if (visa != null && !visa.trim().isEmpty()) {
                String[] parts = visa.split(", ");
                visaTypeLabel.setText("Visa Type: " + (parts.length > 0 ? parts[0] : "N/A"));
                visaCountryLabel.setText("Visa Country: " + (parts.length > 1 ? parts[1] : "N/A"));
            } else {
                visaTypeLabel.setText("Visa Type: N/A");
                visaCountryLabel.setText("Visa Country: N/A");
            }
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
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

    @FXML
    private void handlePrint(ActionEvent event) {
        Stage stage = getStageFromEvent(event);
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean proceed = job.showPrintDialog(stage);
            if (proceed) {
                boolean printed = job.printPage(visaDetailsPane);
                if (printed) {
                    job.endJob();
                    Alert alert = new Alert(AlertType.INFORMATION, "Visa details printed successfully!");
                    alert.initOwner(stage);
                    alert.show();
                } else {
                    Alert alert = new Alert(AlertType.ERROR, "Printing failed.");
                    alert.initOwner(stage);
                    alert.show();
                }
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR, "No printer found.");
            alert.initOwner(stage);
            alert.show();
        }
    }
}
