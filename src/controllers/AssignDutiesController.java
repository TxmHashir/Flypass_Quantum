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

public class AssignDutiesController extends SharedController {
    @FXML private TableView<Duty> availTable, assignedTable;
    @FXML private TableColumn<Duty, String> availTimeCol, availlocCol;
    @FXML private TableColumn<Duty, Integer> availFlightCol;
    @FXML private TableColumn<Duty, String> assignTimeCol, assignlocCol;
    @FXML private TableColumn<Duty, Integer> assignFlightCol;
    
    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        // Available table
        availTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        availlocCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        availFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());

        // Assigned table
        assignTimeCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTime()));
        assignlocCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getLocation()));
        assignFlightCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getflightNo()).asObject());
    }

    public void loadData() {
        availTable.setItems(FXCollections.observableArrayList(
            MockData.getAllDuties().stream()
                .filter(d -> !user.getAssignedDuties().contains(d))
                .collect(Collectors.toList())
        ));

        assignedTable.setItems(FXCollections.observableArrayList(user.getAssignedDuties()));
    }

    @FXML
    private void addDuty() {
        Duty selected = availTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            user.getAssignedDuties().add(selected);
            loadData();
        }
    }

    @FXML
    private void removeDuty() {
        Duty selected = assignedTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            user.getAssignedDuties().remove(selected);
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