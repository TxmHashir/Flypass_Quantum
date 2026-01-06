import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import java.io.File;
public class ProfileController extends SharedController {
@FXML private VBox custPane, pilotPane, adminPane;
@FXML private ImageView custImgView, pilotImgView, adminImgView;
@FXML private Button uploadImgBtnCust, uploadImgBtnAdm, uploadImgBtnPilot;
// Customer
@FXML private Label nameLabel, fNameLabel, lNameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaLabel, countryLabel, cityLabel, postCodeLabel;
// Pilot
@FXML private Label pilotNameLabel, pilotCnicLabel, pilotEmailLabel, pilotContactLabel, pilotPassportLabel, pilotCitizenshipLabel, pilotVisaLabel, pilotBankNameLabel, pilotBankAccLabel, pilotSalaryLabel, pilotCountryLabel, pilotCityLabel, pilotpostCodeLabel;
// Admin
@FXML private Label adminNameLabel, adminCnicLabel, adminEmailLabel, adminContactLabel, adminPassportLabel, adminCitizenshipLabel, adminVisaLabel, adminBankNameLabel, adminBankAccLabel, adminSalaryLabel, adminCountryLabel, adminCityLabel, adminpostCodeLabel;

    @FXML
    private void initialize() {
        // Initialization code
    }

    public void initializeProfile() {
        String role = user.getRole();
        custPane.setVisible(role.equals("customer"));
        pilotPane.setVisible(role.equals("pilot"));
        adminPane.setVisible(role.equals("admin"));

        // Load image for active role
        loadProfileImage();

        // Populate labels based on role
        if (role.equals("customer")) {
            String[] names = user.getName().split(" ");
            fNameLabel.setText(names.length > 0 ? names[0] : "");
            lNameLabel.setText(names.length > 1 ? names[1] : "");
            cnicLabel.setText(user.getCnic());
            emailLabel.setText(user.getEmail());
            contactLabel.setText(user.getContact());
            passportLabel.setText(user.getPassportNumber());
            citizenshipLabel.setText(user.getCitizenship());
            visaLabel.setText(user.getVisa());
            countryLabel.setText(user.getCountry());
            cityLabel.setText(user.getCity());
            postCodeLabel.setText(user.getpostCode());
        } else if (role.equals("pilot")) {
            pilotNameLabel.setText(user.getName());
            pilotCnicLabel.setText(user.getCnic());
            pilotEmailLabel.setText(user.getEmail());
            pilotContactLabel.setText(user.getContact());
            pilotPassportLabel.setText(user.getPassportNumber());
            pilotCitizenshipLabel.setText(user.getCitizenship());
            pilotVisaLabel.setText(user.getVisa());
            pilotBankNameLabel.setText(user.getBankName());
            pilotBankAccLabel.setText(user.getbankAcc());
            pilotSalaryLabel.setText(String.valueOf(user.getSalary()));
            pilotCountryLabel.setText(user.getCountry());
            pilotCityLabel.setText(user.getCity());
            pilotpostCodeLabel.setText(user.getpostCode());
        } else if (role.equals("admin")) {
            adminNameLabel.setText(user.getName());
            adminCnicLabel.setText(user.getCnic());
            adminEmailLabel.setText(user.getEmail());
            adminContactLabel.setText(user.getContact());
            adminPassportLabel.setText(user.getPassportNumber());
            adminCitizenshipLabel.setText(user.getCitizenship());
            adminVisaLabel.setText(user.getVisa());
            adminBankNameLabel.setText(user.getBankName());
            adminBankAccLabel.setText(user.getbankAcc());
            adminSalaryLabel.setText(String.valueOf(user.getSalary()));
            adminCountryLabel.setText(user.getCountry());
            adminCityLabel.setText(user.getCity());
            adminpostCodeLabel.setText(user.getpostCode());
        }
    }

    private void loadProfileImage() {
        try {
            Image image = new Image(getClass().getResourceAsStream(user.getprofImgPath()));
            ImageView activeView = getActiveImageView();
            if (activeView != null) {
                applyImageSettings(activeView, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageView getActiveImageView() {
        String role = user.getRole();
        if (role.equals("customer")) return custImgView;
        if (role.equals("pilot")) return pilotImgView;
        if (role.equals("admin")) return adminImgView;
        return null;
    }

    private void applyImageSettings(ImageView view, Image image) {
        view.setImage(image);
        view.setFitWidth(150);
        view.setFitHeight(150);
        view.setPreserveRatio(true);
        Circle clip = new Circle(75, 75, 75);
        view.setClip(clip);
    }

    @FXML
    private void uploadImage() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try {
                Image newImage = new Image(file.toURI().toString());
                user.setprofImgPath(file.getAbsolutePath());
                ImageView targetView = getActiveImageView();
                if (targetView != null) {
                    applyImageSettings(targetView, newImage);
                }
                // Hide upload buttons
                if (uploadImgBtnCust != null) uploadImgBtnCust.setVisible(false);
                if (uploadImgBtnAdm != null) uploadImgBtnAdm.setVisible(false);
                if (uploadImgBtnPilot != null) uploadImgBtnPilot.setVisible(false);
                UserDAO userDAO = new UserDAO();
                userDAO.updateUser(user);
                new Alert(AlertType.INFORMATION, "Profile image updated successfully!").show();
            } catch (Exception e) {
                new Alert(AlertType.ERROR, "Failed to load image: " + e.getMessage()).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void openBookFlight(ActionEvent event) {
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
    private void openApplyVisa(ActionEvent event) {
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
    private void openViewVisa(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ViewVisa.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
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

    @FXML
    private void openCheckIn(ActionEvent event) {
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
    private void openPilotDuty(ActionEvent event) {
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
            controller.loadDuties();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openDutySchedule(ActionEvent event) {
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
            controller.loadDuties();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manageUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AdminManageUsers.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            
            AdminManageUsersController controller = loader.getController();
            controller.setUser(user);
            controller.loadUsers();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manageFlights(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ManageFlights.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            
            ManageFlightsController controller = loader.getController();
            controller.setUser(user);
            controller.loadFlights();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}