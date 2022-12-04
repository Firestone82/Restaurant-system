package presentationLayer.controller;

import bussinessLayer.objects.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentationLayer.App;
import presentationLayer.controller.list.LoginController;
import presentationLayer.controller.list.TableOrdersController;
import presentationLayer.controller.list.TableViewController;
import presentationLayer.enums.SceneType;

public class MainController {
    private final App app;
    private final Stage primaryStage;

    private LoginController loginController;
    private TableViewController tableViewController;
    private TableOrdersController tableOrdersController;

    private Employee employee = new Employee(-1,"Administrator");

    public MainController(App app, Stage primaryStage) {
        this.app = app;
        this.primaryStage = primaryStage;

        try {
            FXMLLoader menuLoader = new FXMLLoader(App.class.getResource(SceneType.LOGIN.getFXML()));
            Pane menuPane = menuLoader.load();
            loginController = menuLoader.getController();
            loginController.init(app, menuPane);

            FXMLLoader tableViewLoader = new FXMLLoader(App.class.getResource(SceneType.TABLEVIEW.getFXML()));
            Pane tableViewPane = tableViewLoader.load();
            tableViewController = tableViewLoader.getController();
            tableViewController.init(app, tableViewPane);

            FXMLLoader tableOrdersLoader = new FXMLLoader(App.class.getResource(SceneType.ADD.getFXML()));
            Pane tableOrdersView = tableOrdersLoader.load();
            tableOrdersController = tableOrdersLoader.getController();
            tableOrdersController.init(app, tableOrdersView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setEmployee(Employee employee) {
    	this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void changeScene(SceneType type, String ...values) {
        try {
            switch (type) {
                case LOGIN -> primaryStage.setScene(loginController.getScene());

                case TABLEVIEW ->  {
                    primaryStage.setScene(tableViewController.getScene());
                    tableViewController.loadValues(employee.toString());
                }

                case ADD -> {
                    primaryStage.setScene(tableOrdersController.getScene());
                    tableOrdersController.loadValues(employee.toString(),"Table n."+ values[0]);
                }
            }

            primaryStage.setTitle(type.getTitle());
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
