package presentationLayer;

import bussinessLayer.objects.Employee;
import bussinessLayer.services.OrderService;
import bussinessLayer.services.PaymentService;
import bussinessLayer.services.ProductService;
import bussinessLayer.services.TableService;
import dataLayer.connection.IDatabase;
import dataLayer.connection.IDatabase;
import dataLayer.connection.SQLDatabase;
import dataLayer.connection.SqLiteDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import presentationLayer.controller.Controller;
import presentationLayer.enums.SceneType;

public class App extends Application {
    public static final IDatabase database = new SQLDatabase();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final TableService tableService = new TableService();
    private final PaymentService paymentService = new PaymentService();
    private Controller controller;
    private Stage primaryStage;
    private Employee employee = new Employee(-1, "Administrator");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        try {
            controller = new Controller(this, primaryStage);
            controller.changeScene(SceneType.LOGIN);

            primaryStage.resizableProperty().set(false);
            primaryStage.setOnCloseRequest(this::exitProgram);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exitProgram(WindowEvent evt) {
        System.exit(0);
    }

    public Controller getController() {
        return controller;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public TableService getTableService() {
        return tableService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

}
