import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.stream.Collectors;

public class AssignFlightsController extends SharedController {
    @FXML private TableView<Flight> availableTable, assignedTable;
    @FXML private TableColumn<Flight, Integer> availFlightCol, assignFlightCol;
    @FXML private TableColumn<Flight, String> availOriginCol, availDestinationCol, availTimeCol, availStatusCol;
    @FXML private TableColumn<Flight, String> assignOriginCol, assignDestinationCol, assignTimeCol, assignStatusCol;
    
    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        // Available table setup
        availFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
        availOriginCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        availDestinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
        availTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        availStatusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        // Assigned table setup
        assignFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getFlightNumber()).asObject());
        assignOriginCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        assignDestinationCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDestination()));
        assignTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        assignStatusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
    }

    public void loadData() {
        availableTable.setItems(FXCollections.observableArrayList(
            MockData.getAllFlights().stream()
                .filter(f -> !user.getAssignedFlights().contains(f))
                .collect(Collectors.toList())
        ));

        assignedTable.setItems(FXCollections.observableArrayList(user.getAssignedFlights()));
    }

    @FXML
    private void assignFlight() {
        Flight selected = availableTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            user.getAssignedFlights().add(selected);
            loadData();
        }
    }

    @FXML
    private void removeFlight() {
        Flight selected = assignedTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            user.getAssignedFlights().remove(selected);
            loadData();
        }
    }

    @FXML
private void goBack(ActionEvent event) {
    userDAO.updateUser(user); // Persist changes
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
        controller.setUser(this.loggedInUser);  // Updated: Use the preserved loggedInUser (admin)
        controller.loadUsers();  // Optional: Explicitly reload to ensure fresh data
        
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }
}