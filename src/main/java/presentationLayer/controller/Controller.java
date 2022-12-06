package presentationLayer.controller;

import bussinessLayer.objects.Employee;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import presentationLayer.App;
import presentationLayer.controller.list.*;
import presentationLayer.enums.SceneType;

public class Controller {
    private final App app;
    private final Stage primaryStage;

    private LoginController loginController;
    private TableViewController tableViewController;
    private TableOrdersController tableOrdersController;
    private ProductViewController productViewController;
    private OrderPaymentController orderPaymentController;

    public Controller(App app, Stage primaryStage) {
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
            Pane tableOrdersViewPane = tableOrdersLoader.load();
            tableOrdersController = tableOrdersLoader.getController();
            tableOrdersController.init(app, tableOrdersViewPane);

            FXMLLoader productViewLoader = new FXMLLoader(App.class.getResource(SceneType.EDIT.getFXML()));
            Pane productViewPane = productViewLoader.load();
            productViewController = productViewLoader.getController();
            productViewController.init(app, productViewPane);

            FXMLLoader orderPaymentLoader = new FXMLLoader(App.class.getResource(SceneType.PAY.getFXML()));
            Pane orderPaymentViewPane = orderPaymentLoader.load();
            orderPaymentController = orderPaymentLoader.getController();
            orderPaymentController.init(app, orderPaymentViewPane);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeScene(SceneType type, String ...values) {
        try {
            switch (type) {
                case LOGIN -> {
                    primaryStage.setScene(loginController.getScene());
                    loginController.loadValues();
                }

                case TABLEVIEW ->  {
                    primaryStage.setScene(tableViewController.getScene());
                    tableViewController.loadValues();
                }

                case ADD -> {
                    primaryStage.setScene(tableOrdersController.getScene());
                    tableOrdersController.loadValues("Table n."+ values[0]);
                }

                case EDIT -> {
                    primaryStage.setScene(productViewController.getScene());
                    productViewController.loadValues();
                }

                case PAY -> {
                    primaryStage.setScene(orderPaymentController.getScene());
                    orderPaymentController.loadValues("Table n."+ values[0]);
                }
            }

            primaryStage.setTitle(type.getTitle());
            primaryStage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
