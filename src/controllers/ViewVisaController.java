import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ViewVisaController extends SharedController {
    @FXML private VBox visaDetailsPane;
    @FXML private HBox btnBox;
    @FXML private Label headerLabel, visaTypeHeader, nameLabel, cnicLabel, passportLabel, citizenshipLabel,
                       issueDateLabel, expDateLabel, visaNoLabel;
    @FXML private Text mrzLine1, mrzLine2;

    public void iniVisaDetails() {
        String visa = user.getVisa();
        if (visa != null && !visa.isEmpty()) {
            String[] parts = visa.split(", ");
            if (parts.length == 2) {
                String visaType = parts[0].trim();
                String country = parts[1].trim();
                
                headerLabel.setText(country.toUpperCase() + " VISA");
                visaTypeHeader.setText(visaType.toUpperCase() + " VISA");
                nameLabel.setText(user.getName());
                cnicLabel.setText(user.getCnic());
                passportLabel.setText(user.getPassportNo());
                citizenshipLabel.setText(user.getCitizenship());
                
                LocalDate issueDate = LocalDate.now();
                LocalDate expDate = issueDate.plusMonths(6); // Mock 6 months validity
                issueDateLabel.setText(issueDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                expDateLabel.setText(expDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                
                visaNoLabel.setText("VISA-" + new Random().nextInt(1000000));
                
                // Mock MRZ
                mrzLine1.setText("V<" + country.toUpperCase() + "<" + visaType.toUpperCase() + "<" + user.getName().replace(" ", "<") + "<");
                mrzLine2.setText(user.getPassportNo() + "<" + user.getCnic() + "<" + issueDate.format(DateTimeFormatter.ofPattern("yyMMdd")) + "<<");
            } else {
                new Alert(AlertType.ERROR, "Invalid visa format.").show();
            }
        } else {
            new Alert(AlertType.WARNING, "No visa applied yet.").show();
            goBack(null);
        }
    }

    @FXML
    private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            btnBox.setVisible(false);
            
            Scale printScale = new Scale(0.8, 0.8);
            visaDetailsPane.getTransforms().add(printScale);
            
            boolean success = job.printPage(visaDetailsPane);
            
            visaDetailsPane.getTransforms().remove(printScale);
            
            btnBox.setVisible(true);
            
            if (success) {
                job.endJob();
                new Alert(AlertType.INFORMATION, "Visa details sent to printer.").show();
            } else {
                new Alert(AlertType.ERROR, "Printing failed.").show();
            }
        } else {
            new Alert(AlertType.ERROR, "No printer available.").show();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = (event != null)
                    ? getStageFromEvent(event)
                    : (Stage) visaDetailsPane.getScene().getWindow();
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.iniProfile();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}