// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.control.TableView;
// import javafx.scene.control.TableColumn;
// import javafx.stage.Stage;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.property.SimpleIntegerProperty;

// public class DutyController extends SharedController {
//     @FXML private TableView<Duty> dutyTable;
//     @FXML private TableColumn<Duty, String> timeCol, locationCol;
//     @FXML private TableColumn<Duty, Integer> flightCol;

//     @FXML
//     private void initialize() {
//         timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
//         locationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
//         flightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
//         dutyTable.setItems(getMockDuties());
//     }

//     private ObservableList<Duty> getMockDuties() {
//         ObservableList<Duty> list = FXCollections.observableArrayList();
//         list.add(new Duty("08:00-12:00", "LAX Terminal 1", 101));
//         list.add(new Duty("12:00-16:00", "NYC Terminal 2", 102));
//         return list;
//     }

//     @FXML
//     private void goBack(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
//             Stage stage = getStageFromEvent(event);
//             stage.setScene(new Scene(loader.load()));
//             ProfileController controller = loader.getController();
//             controller.setUser(user);
//             controller.initializeProfile();
//             stage.setWidth(700);
//             stage.setHeight(550);
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }




// package app.controllers;

// import app.models.Duty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DutyController extends SharedController {
    @FXML private TableView<Duty> dutyTable;
    @FXML private TableColumn<Duty, String> timeCol, locationCol;
    @FXML private TableColumn<Duty, Integer> flightCol;

    @FXML
    private void initialize() {
        timeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        locationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        flightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
        dutyTable.setItems(getMockDuties());
    }

    private ObservableList<Duty> getMockDuties() {
        ObservableList<Duty> list = FXCollections.observableArrayList();
        list.add(new Duty("08:00-12:00", "LAX Terminal 1", 101));
        list.add(new Duty("12:00-16:00", "NYC Terminal 2", 102));
        return list;
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();  // Preserve position
            double y = stage.getY();
            stage.setScene(new Scene(loader.load()));
            stage.setX(x);  // Restore position
            stage.setY(y);
            DutyController controller = loader.getController();
            controller.setUser(user);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}