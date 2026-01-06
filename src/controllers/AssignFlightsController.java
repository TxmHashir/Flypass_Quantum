import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.stream.Collectors;

public class AssignFlightsController extends SharedController {
    @FXML private TableView<Flight> availTable, assignedTable;
    @FXML private TableColumn<Flight, Integer> availFlightCol, assignFlightCol;
    @FXML private TableColumn<Flight, String> availOriginCol, availdestCol, availTimeCol, availStatusCol;
    @FXML private TableColumn<Flight, String> assignOriginCol, assigndestCol, assignTimeCol, assignStatusCol;
    
    private UserDAO userDAO = new UserDAO();
    private FlightDAO flightDAO = new FlightDAO();

    @FXML
    private void initialize() {
        availFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());
        availOriginCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        availdestCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getdest()));
        availTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        availStatusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));

        assignFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());
        assignOriginCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getOrigin()));
        assigndestCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getdest()));
        assignTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSchedule()));
        assignStatusCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getStatus()));
    }

    public void setUser(User user) {
        this.user = user;
        loadData();
    }

    public void loadData() {
        ObservableList<Flight> available = flightDAO.getAllFlights().stream()
            .filter(f -> !user.getAssignedFlights().contains(f))
            .collect(Collectors.toCollection(FXCollections::observableArrayList));
        availTable.setItems(available);

        assignedTable.setItems(FXCollections.observableArrayList(user.getAssignedFlights()));
    }

    @FXML
    private void addFlight() {
        Flight selected = availTable.getSelectionModel().getSelectedItem();
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
        userDAO.updateUser(user);
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
            controller.setUser(this.loggedInUser);
            controller.loadUsers();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}