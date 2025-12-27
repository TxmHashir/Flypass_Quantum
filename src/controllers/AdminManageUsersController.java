import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdminManageUsersController extends SharedController {
    @FXML private TableView<User> usersTable;
    @FXML private TableColumn<User, String> nameCol, roleCol;
    @FXML private TableColumn<User, Double> salaryCol;
    
    private UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        roleCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRole()));
        salaryCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getSalary()).asObject());
        loadUsers();
    }

    public void loadUsers() {
        ObservableList<User> users = FXCollections.observableArrayList(
            userDAO.getAllUsers().stream()
                .filter(u -> !"admin".equals(u.getRole())) // Exclude admins from list
                .collect(Collectors.toList())
        );
        usersTable.setItems(users);
    }

    @FXML
    private void editSalary() {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(AlertType.WARNING, "Select a user first.").show();
            return;
        }
        TextInputDialog dialog = new TextInputDialog(String.valueOf(selected.getSalary()));
        dialog.setTitle("Edit Salary");
        dialog.setHeaderText("Edit salary for " + selected.getName());
        dialog.setContentText("New Salary:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(salaryStr -> {
            try {
                double salary = Double.parseDouble(salaryStr);
                selected.setSalary(salary);
                userDAO.updateUser(selected);
                usersTable.refresh();
                new Alert(AlertType.INFORMATION, "Salary updated.").show();
            } catch (NumberFormatException e) {
                new Alert(AlertType.ERROR, "Invalid salary.").show();
            }
        });
    }

    @FXML
    private void assignDuties() {
        User selected = usersTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            new Alert(AlertType.WARNING, "Select a user first.").show();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AssignDuties.fxml"));
            Stage stage = (Stage) usersTable.getScene().getWindow();
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            
            AssignDutiesController controller = loader.getController();
            controller.setUser(selected);  // Pass the selected employee
            controller.loadData();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Profile.fxml"));
            Stage stage = getStageFromEvent(event);
            double x = stage.getX();
            double y = stage.getY();
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().addAll(stage.getScene().getStylesheets());
            
            stage.setScene(newScene);
            stage.setX(x);
            stage.setY(y);
            
            ProfileController controller = loader.getController();
            controller.setUser(user); // Preserve original admin user
            controller.initializeProfile();
            
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}