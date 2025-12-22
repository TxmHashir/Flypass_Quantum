// Updated LoginController.java with new animation
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.fxml.FXMLLoader;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class LoginController extends SharedController {
    @FXML private HBox animationBox; // New HBox for the circles
    @FXML private Label instructionLabel; // Label for "One Tap Your Card"

    private UserDAO userDAO = new UserDAO();
    private boolean justSignedOut = false;
    private boolean wasPresent = false;
    private Timeline timeline;

    private List<Circle> circles = new ArrayList<>();
    private List<ScaleTransition> transitions = new ArrayList<>();

    public void setJustSignedOut(boolean value) {
        this.justSignedOut = value;
    }

    private void handleLogin(String encryptedKey) {
        User user = userDAO.getUserByEncryptedKey(encryptedKey);
        if (user != null) {
            openProfile(user);
        } else {
            Platform.runLater(() -> {
                Stage stage = (Stage) instructionLabel.getScene().getWindow();
                if (stage != null) {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid encrypted key from USB.");
                    alert.initOwner(stage);
                    alert.show();
                }
            });
        }
    }

    private void openProfile(User user) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
                Stage stage = (Stage) instructionLabel.getScene().getWindow();
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
        });
    }

    @FXML
    private void goToSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            SignUpController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToForgetPassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ForgetPassword.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());  // Copy theme
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            ForgetPasswordController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkAndLogin() {
        String key = UsbKeyFetcher.fetchEncryptionKeyFromUsb("encryption_key.txt");
        if (key != null) {
            if (!wasPresent) {
                wasPresent = true;
                handleLogin(key);
            }
        } else {
            wasPresent = false;
        }
    }

    @FXML
    private void initialize() {
        // Create 5 overlapping circles with gradient colors
        Color[] colors = {
                Color.web("#6A5ACD", 0.8),  // Blue
                Color.web("#ADD8E6", 0.8),  // Light blue
                Color.web("#9370DB", 0.8),  // Purple
                Color.web("#DDA0DD", 0.8),  // Light purple
                Color.web("#FF69B4", 0.8)   // Pink (adjusted to match images)
        };

        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle(30);  // Radius 30 for visible size
            circle.setFill(colors[i]);
            circle.setStroke(Color.TRANSPARENT);
            circles.add(circle);
            animationBox.getChildren().add(circle);
            // Overlap by setting negative spacing (handled in FXML)
        }

        // Set up pulsing animations with delay for wave effect
        for (int i = 0; i < circles.size(); i++) {
            ScaleTransition st = new ScaleTransition(Duration.millis(800), circles.get(i));
            st.setByX(0.3);
            st.setByY(0.3);
            st.setCycleCount(Animation.INDEFINITE);
            st.setAutoReverse(true);
            st.setDelay(Duration.millis(i * 200));  // Delay for sequential pulsing
            transitions.add(st);
            st.play();
        }

        wasPresent = justSignedOut ? true : false;

        // Start polling for USB key every 5 seconds
        timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> checkAndLogin()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Perform initial check after the window is set
        instructionLabel.sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldScene, Scene newScene) {
                if (newScene != null) {
                    newScene.windowProperty().addListener(new ChangeListener<Window>() {
                        @Override
                        public void changed(ObservableValue<? extends Window> wobservable, Window oldWindow, Window newWindow) {
                            if (newWindow != null) {
                                Platform.runLater(() -> {
                                    checkAndLogin();
                                    
                                });
                                newScene.windowProperty().removeListener(this);
                            }
                        }
                    });
                    instructionLabel.sceneProperty().removeListener(this);
                }
            }
        });
    }
}