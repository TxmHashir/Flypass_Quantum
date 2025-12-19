


// // import javafx.event.ActionEvent;
// // import javafx.fxml.FXML;
// // import javafx.fxml.FXMLLoader;
// // import javafx.scene.Scene;
// // import javafx.scene.control.*;
// // import javafx.stage.Stage;
// // import javafx.collections.FXCollections;
// // import javafx.collections.ObservableList;
// // import javafx.beans.property.SimpleIntegerProperty;
// // import javafx.beans.property.SimpleStringProperty;

// // public class BookFlightController extends SharedController {
// //     @FXML private TextField originField, destinationField, seatsField;
// //     @FXML private DatePicker datePicker;
// //     @FXML private TableView<Flight> flightsTable;
// //     @FXML private TableColumn<Flight, Integer> flightNumberCol;
// //     @FXML private TableColumn<Flight, String> originCol, destinationCol, scheduleCol, statusCol;

// //     @FXML
// //     private void initialize() {
// //         flightNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
// //         originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
// //         destinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
// //         scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
// //         statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
// //     }

// //     @FXML
// //     private void searchFlights() {
// //         ObservableList<Flight> list = FXCollections.observableArrayList();
// //         String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "Unknown";
// //         list.add(new Flight(201, originField.getText(), destinationField.getText(),
// //                 date + " 10:00", "On-Time", "Direct"));
// //         flightsTable.setItems(list);
// //     }

// //     @FXML
// //     private void confirmBooking() {
// //         new Alert(Alert.AlertType.INFORMATION, "Payment successful! Ticket booked.").show();
// //     }

// //     @FXML
// //     private void goBack(ActionEvent event) {
// //         try {
// //             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
// //             Stage stage = getStageFromEvent(event);
// //             stage.setScene(new Scene(loader.load()));
// //             ProfileController controller = loader.getController();
// //             controller.setUser(user);
// //             controller.initializeProfile();
// //             stage.setWidth(700);
// //             stage.setHeight(550);
// //             stage.show();
// //         } catch (Exception e) {
// //             e.printStackTrace();
// //         }
// //     }
// // }

// // import app.models.Flight;
// import javafx.event.ActionEvent;
// import javafx.fxml.FXML;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Scene;
// import javafx.scene.control.*;
// import javafx.stage.Stage;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
// import javafx.beans.property.SimpleIntegerProperty;
// import javafx.beans.property.SimpleStringProperty;

// public class BookFlightController extends SharedController {
//     @FXML private TextField originField, destinationField, seatsField;
//     @FXML private DatePicker datePicker;
//     @FXML private TableView<Flight> flightsTable;
//     @FXML private TableColumn<Flight, Integer> flightNumberCol;
//     @FXML private TableColumn<Flight, String> originCol, destinationCol, scheduleCol, statusCol;

//     @FXML
//     private void initialize() {
//         flightNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
//         originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
//         destinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
//         scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
//         statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
//     }

//     @FXML
//     private void searchFlights() {
//         ObservableList<Flight> list = FXCollections.observableArrayList();
//         String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "Unknown";
//         list.add(new Flight(201, originField.getText(), destinationField.getText(),
//                 date + " 10:00", "On-Time", "Direct"));
//         flightsTable.setItems(list);
//     }

//     @FXML
//     private void confirmBooking() {
//         new Alert(Alert.AlertType.INFORMATION, "Payment successful! Ticket booked.").show();
//     }

//     @FXML
//     private void goBack(ActionEvent event) {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
//             Stage stage = getStageFromEvent(event);
//             double x = stage.getX();  // Preserve position
//             double y = stage.getY();
//             stage.setScene(new Scene(loader.load()));
//             stage.setX(x);  // Restore position
//             stage.setY(y);
//             ProfileController controller = loader.getController();
//             controller.setUser(user);
//             controller.initializeProfile();
//             stage.show();
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookFlightController extends SharedController {
    @FXML private TextField originField, destinationField, seatsField;
    @FXML private DatePicker datePicker;
    @FXML private TableView<Flight> flightsTable;
    @FXML private TableColumn<Flight, Integer> flightNumberCol;
    @FXML private TableColumn<Flight, String> originCol, destinationCol, scheduleCol, statusCol;

    @FXML
    private void initialize() {
        flightNumberCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
        originCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        destinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
        scheduleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        statusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
    }

    @FXML
    private void searchFlights() {
        ObservableList<Flight> list = FXCollections.observableArrayList();
        String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : "Unknown";
        list.add(new Flight(201, originField.getText(), destinationField.getText(),
                date + " 10:00", "On-Time", "Direct"));
        flightsTable.setItems(list);
    }

    @FXML
    private void confirmBooking() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Payment successful! Ticket booked.");
        Stage stage = (Stage) originField.getScene().getWindow();
        alert.initOwner(stage);
        alert.show();
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
            ProfileController controller = loader.getController();
            controller.setUser(user);
            controller.initializeProfile();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}