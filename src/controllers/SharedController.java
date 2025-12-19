import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.io.IOException;

public class SharedController {
    protected User user;

    public void setUser(User user) {
        this.user = user;
    }

    public void signOut(ActionEvent event) {
        try {
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"))));
            stage.setX(x);  // Restore position
            stage.setY(y);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
            stage.setY(y);
            SettingsController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Stage getStageFromEvent(ActionEvent event) {
        return (Stage)((Node)event.getSource()).getScene().getWindow();
    }
}