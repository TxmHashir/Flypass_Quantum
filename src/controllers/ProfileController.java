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
import javafx.scene.layout.StackPane;
import java.io.File;

public class ProfileController extends SharedController {
    @FXML private VBox customerPane, staffPane, pilotPane, adminPane, airHostessPane;
    @FXML private ImageView custImgView, staffImageView, pilotImageView, airHostessImageView, adminImageView;
    @FXML private Button uploadImageButtonCustomer, uploadImageButtonStaff, uploadImageButtonAdmin, uploadImageButtonPilot, uploadImageButtonAirHostess;
    // Customer
    @FXML private Label nameLabel, firstNameLabel, lNameLabel, cnicLabel, emailLabel, contactLabel, passportLabel, citizenshipLabel, visaStatusLabel, roleLabel, locationLabel, dobLabel, userRoleLabel, salaryLabel;
    @FXML private Label countryLabel, cityLabel, postalCodeLabel;
    // Pilot
    @FXML private Label pilotNameLabel, pilotNameLabel2, pilotCnicLabel, pilotEmailLabel, pilotContactLabel, pilotPassportLabel, pilotCitizenshipLabel, pilotVisaLabel, pilotVisaStatusLabel, pilotRoleLabel, pilotRoleLabel2, pilotSalaryLabel;
    @FXML private Label pilotCountryLabel, pilotCityLabel, pilotPostalCodeLabel;
    @FXML private Label staffNameLabel, staffNameLabel2, staffCnicLabel, staffEmailLabel, staffContactLabel, staffPassportLabel, staffCitizenshipLabel, staffVisaLabel, staffVisaStatusLabel, staffRoleLabel, staffRoleDisplayLabel, staffSalaryLabel;
    @FXML private Label staffCountryLabel, staffCityLabel, staffPostalCodeLabel;
    @FXML private Label ahNameLabel, ahNameLabel2, ahCnicLabel, ahEmailLabel, ahContactLabel, ahPassportLabel, ahCitizenshipLabel, ahVisaLabel, ahVisaStatusLabel, ahRoleLabel, ahRoleLabel2, ahSalaryLabel;
    @FXML private Label ahCountryLabel, ahCityLabel, ahPostalCodeLabel;
    // Admin
    @FXML private Label adminNameLabel, adminNameLabel2, adminCnicLabel, adminEmailLabel, adminContactLabel, adminPassportLabel, adminCitizenshipLabel, adminVisaLabel, adminVisaStatusLabel, adminRoleLabel, adminRoleLabel2, adminSalaryLabel;
    @FXML private Label adminCountryLabel, adminCityLabel, adminPostalCodeLabel;

    public void iniProfile() {
        if (user == null) return;
        hideAllPanes();
        loadProfileImg();
        switch (user.getRole().toLowerCase()) {
            case "customer":
                customerPane.setVisible(true);
                populateCustomerProfile();
                break;
            case "pilot":
                pilotPane.setVisible(true);
                populatePilotProfile();
                break;
            case "staff":
                staffPane.setVisible(true);
                populateStaffProfile();
                break;
            case "airhostess":
                airHostessPane.setVisible(true);
                populateAirHostessProfile();
                break;
            case "admin":
                adminPane.setVisible(true);
                populateAdminProfile();
                break;
            default:
                new Alert(AlertType.ERROR, "Unknown role.").show();
        }
    }

    private void hideAllPanes() {
        customerPane.setVisible(false);
        pilotPane.setVisible(false);
        staffPane.setVisible(false);
        airHostessPane.setVisible(false);
        adminPane.setVisible(false);
    }

    private ImageView getActiveImgView() {
    if (user == null || user.getRole() == null) return null;
    String role = user.getRole().toLowerCase();
    switch (role) {
    case "customer": return custImgView;
    case "pilot": return pilotImageView;
    case "staff": return staffImageView;
    case "airhostess":
    case "air_hostess": return airHostessImageView;
    case "admin": return adminImageView;
    default: return custImgView;
    }
    }
    private void loadProfileImg() {
    try {
    String imagePath = user.getprofImgPath();
    Image image = null;
    ImageView targetView = getActiveImgView();
    if (targetView == null) return;
    if (imagePath != null && (imagePath.startsWith("/") || imagePath.contains(":"))) {
    try {
    File imageFile = new File(imagePath);
    if (imageFile.exists()) {
    image = new Image(imageFile.toURI().toString(), 120, 120, true, true);
    } else {
    image = new Image("titleicon.png", 120, 120, true, true);
    }
    } catch (Exception e) {
    image = new Image("titleicon.png", 120, 120, true, true);
    }
    } else {
    image = new Image(imagePath != null ? imagePath : "titleicon.png", 120, 120, true, true);
    }
    applyImgSettings(targetView, image);
    boolean showButton = imagePath == null || imagePath.isEmpty() || imagePath.equals("titleicon.png") || image.isError();
    if (uploadImageButtonCustomer != null) {
    uploadImageButtonCustomer.setVisible(showButton);
    }
    if (uploadImageButtonStaff != null) {
    uploadImageButtonStaff.setVisible(showButton);
    }
    if (uploadImageButtonAdmin != null) {
    uploadImageButtonAdmin.setVisible(showButton);
    }
    if (uploadImageButtonPilot != null) {
    uploadImageButtonPilot.setVisible(showButton);
    }
    if (uploadImageButtonAirHostess != null) {
    uploadImageButtonAirHostess.setVisible(showButton);
    }
    } catch (Exception e) {
    System.err.println("Failed to load profile image: " + e.getMessage());
    if (uploadImageButtonCustomer != null) {
    uploadImageButtonCustomer.setVisible(true);
    }
    if (uploadImageButtonStaff != null) {
    uploadImageButtonStaff.setVisible(true);
    }
    if (uploadImageButtonAdmin != null) {
    uploadImageButtonAdmin.setVisible(true);
    }
    if (uploadImageButtonPilot != null) {
    uploadImageButtonPilot.setVisible(true);
    }
    if (uploadImageButtonAirHostess != null) {
    uploadImageButtonAirHostess.setVisible(true);
    }
    }
    }
    private void populateCustomerProfile() {
    String fullName = user.getName() != null ? user.getName() : "N/A";
    nameLabel.setText(fullName);
    firstNameLabel.setText(fullName);
    roleLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "CUSTOMER");
    String location = "";
    if (user.getCity() != null && !user.getCity().isEmpty()) {
    location = user.getCity();
    if (user.getCountry() != null && !user.getCountry().isEmpty()) {
    location += ", " + user.getCountry();
    }
    } else if (user.getCountry() != null && !user.getCountry().isEmpty()) {
    location = user.getCountry();
    }
    locationLabel.setText(location.isEmpty() ? "N/A" : location);
    cnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
    emailLabel.setText(user.getEmail() != null ? user.getEmail() : "N/A");
    contactLabel.setText(user.getContact() != null ? user.getContact() : "N/A");
    userRoleLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "N/A");
    if (salaryLabel != null) {
    if (user.getSalary() > 0) {
    salaryLabel.setText(String.format("Rs%,.2f", user.getSalary()));
    } else {
    salaryLabel.setText("N/A");
    }
    }
     countryLabel.setText(user.getCountry() != null ? user.getCountry() : "N/A");
     cityLabel.setText(user.getCity() != null ? user.getCity() : "N/A");
     postalCodeLabel.setText(user.getpostCode() != null ? user.getpostCode() : "N/A");
    }
    private void populatePilotProfile() {
    String fullName = user.getName() != null ? user.getName() : "N/A";
    if (pilotNameLabel != null) {
    pilotNameLabel.setText(fullName);
    }
    if (pilotNameLabel2 != null) {
    pilotNameLabel2.setText(fullName);
    }
    if (pilotRoleLabel != null) {
    pilotRoleLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "PILOT");
    }
    if (pilotCnicLabel != null) {
    pilotCnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
    }
    if (pilotEmailLabel != null) {
    pilotEmailLabel.setText(user.getEmail() != null ? user.getEmail() : "N/A");
    }
    if (pilotContactLabel != null) {
    pilotContactLabel.setText(user.getContact() != null ? user.getContact() : "N/A");
    }
    if (pilotRoleLabel2 != null) {
    pilotRoleLabel2.setText(user.getRole() != null ? user.getRole().toUpperCase() : "N/A");
    }
    if (pilotSalaryLabel != null) {
    pilotSalaryLabel.setText(String.format("Rs%,.2f", user.getSalary()));
    }
    if (pilotCountryLabel != null) {
    pilotCountryLabel.setText(user.getCountry() != null ? user.getCountry() : "N/A");
    }
    if (pilotCityLabel != null) {
    pilotCityLabel.setText(user.getCity() != null ? user.getCity() : "N/A");
    }
    if (pilotPostalCodeLabel != null) {
    pilotPostalCodeLabel.setText(user.getpostCode() != null ? user.getpostCode() : "N/A");
    }
    }
    private void populateStaffProfile() {
    String fullName = user.getName() != null ? user.getName() : "N/A";
    staffNameLabel.setText(fullName);
    staffNameLabel2.setText(fullName);
    staffRoleDisplayLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "N/A");
    staffEmailLabel.setText(user.getEmail() != null ? user.getEmail() : "N/A");
    staffContactLabel.setText(user.getContact() != null ? user.getContact() : "N/A");
    staffRoleLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "N/A");
    if (staffSalaryLabel != null) {
    staffSalaryLabel.setText(user.getSalary() > 0 ? String.format("$%,.2f", user.getSalary()) : "N/A");
    }
    if (staffCnicLabel != null) {
    staffCnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
    }
    staffCountryLabel.setText(user.getCountry() != null ? user.getCountry() : "N/A");
    staffCityLabel.setText(user.getCity() != null ? user.getCity() : "N/A");
    staffPostalCodeLabel.setText(user.getpostCode() != null ? user.getpostCode() : "N/A");
    }
    private void populateAirHostessProfile() {
    String fullName = user.getName() != null ? user.getName() : "N/A";
    if (ahNameLabel != null) {
    ahNameLabel.setText(fullName);
    }
    if (ahNameLabel2 != null) {
    ahNameLabel2.setText(fullName);
    }
    String roleDisplay = user.getRole() != null ? user.getRole().toUpperCase().replace("_", " ") : "AIR HOSTESS";
    if (ahRoleLabel != null) {
    ahRoleLabel.setText(roleDisplay);
    }
    if (ahCnicLabel != null) {
    ahCnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
    }
    if (ahEmailLabel != null) {
    ahEmailLabel.setText(user.getEmail() != null ? user.getEmail() : "N/A");
    }
    if (ahContactLabel != null) {
    ahContactLabel.setText(user.getContact() != null ? user.getContact() : "N/A");
    }
    if (ahRoleLabel2 != null) {
    ahRoleLabel2.setText(roleDisplay);
    }
    if (ahSalaryLabel != null) {
    ahSalaryLabel.setText(String.format("Rs%,.2f", user.getSalary()));
    }
    if (ahCountryLabel != null) {
    ahCountryLabel.setText(user.getCountry() != null ? user.getCountry() : "N/A");
    }
    if (ahCityLabel != null) {
    ahCityLabel.setText(user.getCity() != null ? user.getCity() : "N/A");
    }
    if (ahPostalCodeLabel != null) {
    ahPostalCodeLabel.setText(user.getpostCode() != null ? user.getpostCode() : "N/A");
    }
    }
    private void applyImgSettings(ImageView targetView, Image img) {
        if (targetView == null || img == null) return;
        targetView.setVisible(true);
        targetView.setOpacity(1.0);
        targetView.setSmooth(true);
        targetView.setPreserveRatio(true);
        targetView.setFitWidth(120);
        targetView.setFitHeight(120);
        targetView.setImage(img);
        if (targetView.getParent() instanceof StackPane) {
            StackPane parent = (StackPane) targetView.getParent();
            parent.getChildren().stream()
                .filter(node -> node instanceof Circle && ((Circle) node).getRadius() >= 60)
                .forEach(node -> node.setVisible(false));
        }

        Circle clip = new Circle(60, 60, 60);
        targetView.setClip(clip);

        if (targetView.getParent() != null) {
            targetView.getParent().requestLayout();
        }

        if (targetView.getImage() != null) {
            System.out.println("ImageView has image: " + targetView.getImage().getWidth() + "x" + targetView.getImage().getHeight());
        } else {
            System.err.println("WARNING: ImageView image is null!");
        }
    }

    private void populateAdminProfile() {
    String fullName = user.getName() != null ? user.getName() : "N/A";
    if (adminNameLabel != null) {
    adminNameLabel.setText(fullName);
    }
    if (adminNameLabel2 != null) {
    adminNameLabel2.setText(fullName);
    }
    if (adminRoleLabel != null) {
    adminRoleLabel.setText(user.getRole() != null ? user.getRole().toUpperCase() : "ADMIN");
    }
    if (adminCnicLabel != null) {
    adminCnicLabel.setText(user.getCnic() != null ? user.getCnic() : "N/A");
    }
    if (adminEmailLabel != null) {
    adminEmailLabel.setText(user.getEmail() != null ? user.getEmail() : "N/A");
    }
    if (adminContactLabel != null) {
    adminContactLabel.setText(user.getContact() != null ? user.getContact() : "N/A");
    }
    if (adminRoleLabel2 != null) {
    adminRoleLabel2.setText(user.getRole() != null ? user.getRole().toUpperCase() : "N/A");
    }
    if (adminSalaryLabel != null) {
    adminSalaryLabel.setText(String.format("Rs%,.2f", user.getSalary()));
    }
    if (adminCountryLabel != null) {
    adminCountryLabel.setText(user.getCountry() != null ? user.getCountry() : "N/A");
    }
    if (adminCityLabel != null) {
    adminCityLabel.setText(user.getCity() != null ? user.getCity() : "N/A");
    }
    if (adminPostalCodeLabel != null) {
    adminPostalCodeLabel.setText(user.getpostCode() != null ? user.getpostCode() : "N/A");
    }
    }
    @FXML
    private void bookFlight(ActionEvent event) {
    navigateTo("/fxml/BookFlight.fxml", event);
    }
    @FXML
    private void applyVisa(ActionEvent event) {
    navigateTo("/fxml/ApplyVisa.fxml", event);
    }
    @FXML
    private void viewVisa(ActionEvent event) {
    if (user.getVisa() == null || user.getVisa().isEmpty()) {
    new Alert(AlertType.WARNING, "No visa applied yet.").show();
    return;
    }
    navigateTo("/fxml/ViewVisa.fxml", event);
    }
    @FXML
    private void checkIn(ActionEvent event) {
    navigateTo("/fxml/CheckIn.fxml", event);
    }
    @FXML
    private void checkInPsngr(ActionEvent event) {
    navigateTo("/fxml/CheckIn.fxml", event);
    }
    @FXML
    private void viewDuties(ActionEvent event) {
    navigateTo("/fxml/DutySchedule.fxml", event);
    }
    @FXML
    private void viewStaffDuties(ActionEvent event) {
    navigateTo("/fxml/DutySchedule.fxml", event);
    }
    @FXML
    private void viewPilotDuties(ActionEvent event) {
    navigateTo("/fxml/PilotDuty.fxml", event);
    }
    @FXML
    private void viewAirHostessDuties(ActionEvent event) {
    navigateTo("/fxml/AirHostessDuty.fxml", event);
    }
    @FXML
    private void manageUsers(ActionEvent event) {
    navigateTo("/fxml/AdminManageUsers.fxml", event);
    }
    @FXML
    private void manageFlights(ActionEvent event) {
    navigateTo("/fxml/ManageFlights.fxml", event);
    }
    private void navigateTo(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
           
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
           
            SharedController controller = loader.getController();
            controller.setUser(user);

            if (fxmlPath.equals("/fxml/ViewVisa.fxml")) {
                ViewVisaController visaController = loader.getController();
                visaController.iniVisaDetails();
            } else if (fxmlPath.equals("/fxml/Profile.fxml")) {
                ProfileController profileController = loader.getController();
                profileController.iniProfile();
            } else if (fxmlPath.equals("/fxml/DutySchedule.fxml")) {
                DutyController dutyController = loader.getController();
                dutyController.loadDuties();
            } else if (fxmlPath.equals("/fxml/PilotDuty.fxml")) {
                PilotDutyController pilotController = loader.getController();
                pilotController.loadDuties();
            } else if (fxmlPath.equals("/fxml/AirHostessDuty.fxml")) {
                AirHostessDutyController ahController = loader.getController();
                ahController.loadDuties();
            }
           
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(AlertType.ERROR, "Failed to load page: " + e.getMessage()).show();
        }
    }
    @FXML
    public void signOut(ActionEvent event) {
    super.signOut(event);
    }

    @FXML
    public void openSettings(ActionEvent event) {
    super.openSettings(event);
    }
    @FXML
    private void toggleTheme(ActionEvent event) {
    Stage stage = getStageFromEvent(event);
    if (stage != null) {
    Scene scene = stage.getScene();
    boolean isCurrentlyDark = scene.getStylesheets().stream()
    .anyMatch(s -> s.contains("dark-theme.css"));
    scene.getStylesheets().clear();
    if (isCurrentlyDark) {
    scene.getStylesheets().add(getClass().getResource("/css/light-theme.css").toExternalForm());
    } else {
    scene.getStylesheets().add(getClass().getResource("/css/dark-theme.css").toExternalForm());
    }
    }
    }
    @FXML
    private void uploadProfileImg(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
    Stage stage = getStageFromEvent(event);
    File selectedFile = fileChooser.showOpenDialog(stage);
    if (selectedFile != null) {
    try {
    String imagePath = selectedFile.getAbsolutePath();
    System.out.println("Selected image path: " + imagePath);
    user.setprofImgPath(imagePath);
    String imageUri = selectedFile.toURI().toString();
    System.out.println("Loading image from URI: " + imageUri);
    Image newImage = new Image(imageUri, 240, 240, true, true);
    System.out.println("Loading image at 240x240 to fill 120px circle");
    newImage.progressProperty().addListener((obs, oldVal, newVal) -> {
    if (newVal.doubleValue() == 1.0) {
    System.out.println("Image loaded successfully");
    }
    });
    if (newImage.isError()) {
    String errorMsg = newImage.getException() != null ? newImage.getException().getMessage() : "Unknown error";
    System.err.println("Image loading error: " + errorMsg);
    throw new Exception("Failed to load image: " + errorMsg);
    }
     ImageView targetView = getActiveImgView();
     if (targetView == null) {
     System.err.println("No active ImageView for role!");
     throw new Exception("Profile ImageView is not initialized for this role");
     }
     applyImgSettings(targetView, newImage);
    if (uploadImageButtonCustomer != null) {
    uploadImageButtonCustomer.setVisible(false);
    }
    if (uploadImageButtonStaff != null) {
    uploadImageButtonStaff.setVisible(false);
    }
    if (uploadImageButtonAdmin != null) {
    uploadImageButtonAdmin.setVisible(false);
    }
    if (uploadImageButtonPilot != null) {
    uploadImageButtonPilot.setVisible(false);
    }
    if (uploadImageButtonAirHostess != null) {
    uploadImageButtonAirHostess.setVisible(false);
    }
    UserDAO userDAO = new UserDAO();
    userDAO.updateUser(user);
    new Alert(AlertType.INFORMATION, "Profile image updated successfully!").show();
    } catch (Exception e) {
    new Alert(AlertType.ERROR, "Failed to load image: " + e.getMessage()).show();
    System.err.println("Error loading image: " + e.getMessage());
    e.printStackTrace();
    }
    }
    }
}