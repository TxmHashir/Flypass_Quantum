import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
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

    public void initializeVisaDetails() {
        String visa = user.getVisa();
        if (visa != null && !visa.isEmpty()) {
            String[] parts = visa.split(", ");
            if (parts.length == 2) {
                String visaType = parts[0].trim();
                String country = parts[1].trim();
                
                headerLabel.setText(country.toUpperCase() + " VISA");
                visaTypeHeader.setText("Type: " + visaType);
                
                nameLabel.setText(user.getName() != null ? user.getName() : "N/A");
                cnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
                passportLabel.setText(user.getPassportNumber() != null ? user.getPassportNumber() : "N/A");
                citizenshipLabel.setText(user.getCitizenship() != null ? user.getCitizenship() : "N/A");
                
                LocalDate now = LocalDate.now();
                issueDateLabel.setText(now.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                expDateLabel.setText(now.plusYears(1).format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                
                visaNoLabel.setText("Visa No: V" + new Random().nextInt(1000000));
                
                mrzLine1.setText("V<" + country.toUpperCase() + "<" + visaType.toUpperCase() + "<<" + user.getName().toUpperCase().replace(" ", "<") + "<<<<<<<<<<<<<<<<<<<");
                mrzLine2.setText(user.getPassportNumber() + "<<" + country.toUpperCase() + "<" + now.format(DateTimeFormatter.ofPattern("yyMMdd")) + "<<<<<<<<<<<<<<<<<<<");
            }
        }
    }

    @FXML
    private void handlePrint(ActionEvent event) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(getStageFromEvent(event))) {
            btnBox.setVisible(false);
            
            visaDetailsPane.layout();
            
            PageLayout pageLayout = job.getJobSettings().getPageLayout();
            
            double nodeWidth = visaDetailsPane.getBoundsInLocal().getWidth();
            double nodeHeight = visaDetailsPane.getBoundsInLocal().getHeight();
            
            double scaleX = pageLayout.getPrintableWidth() / nodeWidth;
            double scaleY = pageLayout.getPrintableHeight() / nodeHeight;
            double scale = Math.min(scaleX, scaleY);
            
            Scale printScale = new Scale(scale, scale);
            visaDetailsPane.getTransforms().add(printScale);
            
            boolean success = job.printPage(pageLayout, visaDetailsPane);
            
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
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
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
}