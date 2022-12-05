package presentationLayer.controller.list;

import bussinessLayer.objects.Employee;
import bussinessLayer.scripts.LoginScript;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import presentationLayer.controller.AbstractController;
import presentationLayer.enums.SceneType;

public class LoginController extends AbstractController {
    @FXML TextField employeeID;
    @FXML TextField employeePassword;

    @Override
    public void initComponents() {

    }

    @FXML
    public void exitPress() {
        System.exit(0);
    }

    @FXML
    public void loginPress() {
        LoginScript loginScript = new LoginScript();
        Pair<Boolean, Employee> result = loginScript.processLogin(employeeID.getText(), employeePassword.getText());

        if (result.getKey()) {
            app.setEmployee(result.getValue());
            app.getController().changeScene(SceneType.TABLEVIEW);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Please check your credentials and try again.");
            alert.showAndWait();
        }
    }

    @Override
    public void loadValues(String... args) {
        app.setEmployee(null);
        employeeID.setText("");
        employeePassword.setText("");
    }
}
