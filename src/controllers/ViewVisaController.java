import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
                
                LocalDate today = LocalDate.now();
                issueDateLabel.setText(today.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                expDateLabel.setText(today.plusYears(1).format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
                
                visaNoLabel.setText(generateVisaNo());
                
                mrzLine1.setText(generateMrzLine1(visaType, country));
                mrzLine2.setText(generateMrzLine2());
            }
        }
    }

    private String generateVisaNo() {
        Random rand = new Random();
        return String.format("V%07d", rand.nextInt(10000000));
    }

    private String generateMrzLine1(String visaType, String country) {
        String typeCode = visaType.substring(0, 1).toUpperCase() + "V";
        String countryCode = country.substring(0, 3).toUpperCase();
        String visaNum = visaNoLabel.getText().substring(1);
        
        return typeCode + "<" + countryCode + visaNum + "<<" + 
               user.getName().toUpperCase().replace(" ", "<") + 
               new String(new char[44 - (2 + 3 + visaNum.length() + 2 + user.getName().length())]).replace("\0", "<");
    }

    private String generateMrzLine2() {
        String passport = user.getPassportNo().toUpperCase();
        String countryCode = user.getCitizenship().substring(0, 3).toUpperCase();
        String dobStr = (user.getDob() != null && !user.getDob().isEmpty()) ? user.getDob().replace("-", "").substring(2) : "000000";  // YYMMDD
        String exp = LocalDate.parse(expDateLabel.getText(), DateTimeFormatter.ofPattern("dd MMM yyyy"))
                             .format(DateTimeFormatter.ofPattern("yyMMdd"));
        String gender = "M"; // Assuming default, adjust if needed
        
        return passport + "<<" + countryCode + dobStr + "<" + user.getCnic().substring(0,1) + gender +
               exp + "<" + countryCode + new String(new char[44 - (passport.length() + 2 + countryCode.length() + dobStr.length() + 1 + 1 + 1 + exp.length() + 1 + countryCode.length())]).replace("\0", "<");
    }

    @FXML
    private void handlePrint() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            if (job.showPageSetupDialog(visaDetailsPane.getScene().getWindow())) {
                if (job.showPrintDialog(visaDetailsPane.getScene().getWindow())) {
                    PageLayout pageLayout = job.getJobSettings().getPageLayout();
                    double pageWidth = pageLayout.getPrintableWidth();
                    double pageHeight = pageLayout.getPrintableHeight();

                    // Force layout
                    visaDetailsPane.layout();

                    // Take snapshot
                    SnapshotParameters params = new SnapshotParameters();
                    WritableImage snapshot = visaDetailsPane.snapshot(params, null);

                    // Create ImageView for printing
                    ImageView printView = new ImageView(snapshot);

                    // Calculate scale to fit page with margins
                    double scaleX = pageWidth / snapshot.getWidth();
                    double scaleY = pageHeight / snapshot.getHeight();
                    double scale = Math.min(scaleX, scaleY) * 0.9;

                    printView.setFitWidth(snapshot.getWidth() * scale);
                    printView.setFitHeight(snapshot.getHeight() * scale);
                    printView.setPreserveRatio(true);

                    boolean success = job.printPage(printView);

                    if (success) {
                        job.endJob();
                        new Alert(AlertType.INFORMATION, "Visa details sent to printer.").show();
                    } else {
                        new Alert(AlertType.ERROR, "Printing failed.").show();
                    }
                }
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