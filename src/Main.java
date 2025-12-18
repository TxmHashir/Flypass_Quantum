import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  @Override
public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    Scene scene = new Scene(loader.load());

    // This background gradient is what makes the glass alert look like it's "floating"
    scene.getRoot().setStyle("-fx-background-color: linear-gradient(to bottom right, #e0eafc, #cfdef3);");

    scene.getStylesheets().add(getClass().getResource("/css/light-theme.css").toExternalForm());
    
    stage.setScene(scene);
    stage.setTitle("Flypass Quantum");
    stage.setWidth(700);
    stage.setHeight(550);
    stage.centerOnScreen();
    stage.show();
}
    public static void main(String[] args) {
        launch(args);
    }
}