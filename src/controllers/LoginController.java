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
    @FXML private HBox animationBox;
    @FXML private Label instuctLabel;

    private UserDAO userDAO = new UserDAO();
    private boolean justSignedOut = false;
    private boolean wasPresent = false;
    private Timeline timeline;

    private List<Circle> circles = new ArrayList<>();
    private List<ScaleTransition> transitions = new ArrayList<>();

    public void setJustSignedOut(boolean value) {
        this.justSignedOut = value;
    }
    
    public void startUsbPolling() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> checkAndLogin()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void checkAndLogin() {
        String key = UsbKeyFetcher.fetchEncryptionKeyFromUsb("encrypted_key.txt");
        if (key != null) {
            if (!wasPresent) {
                User user = userDAO.getUserByEncrypKey(key);
                if (user != null) {
                    loginUser(user);
                } else {
                    new Alert(AlertType.ERROR, "User not found.").show();
                }
                wasPresent = true;
            }
        } else {
            wasPresent = false;
        }
    }

    private void loginUser(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = (Stage) instuctLabel.getScene().getWindow();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);

            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        Color[] colors = {
                Color.web("#6A5ACD", 0.8),  
                Color.web("#ADD8E6", 0.8), 
                Color.web("#9370DB", 0.8), 
                Color.web("#DDA0DD", 0.8), 
                Color.web("#FF69B4", 0.8)   
        };

        for (int i = 0; i < 5; i++) {
            Circle circle = new Circle(30); 
            circle.setFill(colors[i]);
            circle.setStroke(Color.TRANSPARENT);
            circles.add(circle);
            animationBox.getChildren().add(circle);
        }
        for (int i = 0; i < circles.size(); i++) {
            ScaleTransition st = new ScaleTransition(Duration.millis(800), circles.get(i));
            st.setByX(0.3);
            st.setByY(0.3);
            st.setCycleCount(Animation.INDEFINITE);
            st.setAutoReverse(true);
            st.setDelay(Duration.millis(i * 200));
            transitions.add(st);
            st.play();
        }

        wasPresent = justSignedOut ? true : false;

        startUsbPolling();
        instuctLabel.sceneProperty().addListener(new ChangeListener<Scene>() {
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
                    instuctLabel.sceneProperty().removeListener(this);
                }
            }
        });
    }

    @FXML
    private void goToSignUp(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SignUp.fxml"));
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToForgetPassword(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ForgetPassword.fxml"));
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}