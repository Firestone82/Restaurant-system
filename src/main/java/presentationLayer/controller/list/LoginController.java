package presentationLayer.controller.list;

import bussinessLayer.objects.Employee;
import bussinessLayer.scripts.LoginScript;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Pair;
import presentationLayer.App;
import presentationLayer.enums.SceneType;

public class LoginController {
    private App app;
    private Pane pane;
    private Scene scene;

    @FXML TextField employeeID;
    @FXML TextField employeePassword;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;

        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());
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
            app.getMainController().setEmployee(result.getValue());
            app.getMainController().changeScene(SceneType.TABLEVIEW);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Login failed");
            alert.setContentText("Please check your credentials and try again.");
            alert.showAndWait();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
