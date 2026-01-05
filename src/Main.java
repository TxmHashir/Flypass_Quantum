import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
    
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/light-theme.css").toExternalForm());
        stage.setScene(scene);
        Image image = new Image("titleicon.png");
        stage.getIcons().add(image);
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