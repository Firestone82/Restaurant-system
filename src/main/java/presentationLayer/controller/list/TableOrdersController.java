package presentationLayer.controller.list;

import bussinessLayer.objects.Order;
import bussinessLayer.objects.Product;
import bussinessLayer.services.OrderService;
import bussinessLayer.services.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import presentationLayer.App;
import presentationLayer.enums.SceneType;

import java.util.ArrayList;
import java.util.List;

public class TableOrdersController {
    private App app;
    private Pane pane;
    private Scene scene;

    private @FXML Label selectedTable;
    private @FXML Label userName;

    private @FXML ComboBox<Product> productAddName;
    private @FXML Spinner<Integer> productAddCount;

    private @FXML TableView<Product> productView;
    private @FXML TableColumn<Product, String> productName;
    private @FXML TableColumn<Product, String> productType;
    private @FXML TableColumn<Product, Integer> productCount;
    private @FXML TableColumn<Product, Double> productTotal;

    private final OrderService orderService = new OrderService();
    private Order order;

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;
        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());

        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        load();
    }

    public void loadValues(String userName, String tableID) {
        this.userName.setText(userName);
        this.selectedTable.setText(tableID);

        this.productView.getItems().clear();
        this.productAddCount.decrement(Integer.MAX_VALUE);
        this.productAddName.getSelectionModel().clearSelection();
        this.order = orderService.insertOrder(Integer.parseInt(tableID.split("\\.")[1]), app.getMainController().getEmployee().getEmployeeID());
        System.out.println("Creating new order n."+ order.getID());
    }

    public void load() {
        productAddName.setItems(FXCollections.observableArrayList(ProductService.getAllProducts()));
        productAddName.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });
        productAddName.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });
        productAddCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500,1));
        productView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    }

    @FXML
    public void addPress() {
        System.out.println("Adding "+ productAddCount.getValue() + " " + productAddName.getValue().getName());

        Product selectedProduct = productAddName.getItems().stream().filter(p -> p.getName().equals(productAddName.getValue().getName())).findFirst().get();
        int selectedCount = productAddCount.getValue();

        for (Product product : productView.getItems()) {
            if (product == selectedProduct) {
                product.setCount(product.getCount() + selectedCount);
                productView.refresh();
                return;
            }
        }

        selectedProduct.setCount(selectedCount);
        productView.getItems().add(selectedProduct);
    }

    @FXML
    public void removePress() {
        System.out.println("Removing "+ productAddCount.getValue() + " " + productAddName.getValue().getName());

        Product selectedProduct = productAddName.getItems().stream().filter(p -> p.getName().equals(productAddName.getValue().getName())).findFirst().get();
        int selectedCount = productAddCount.getValue();

        for (Product product : List.copyOf(productView.getItems())) {
            if (product == selectedProduct) {
                product.setCount(product.getCount() - selectedCount);

                if (product.getCount() <= 0) {
                    productView.getItems().remove(product);
                }

                productView.refresh();
                return;
            }
        }
    }

    @FXML
    public void finishPress() {
        System.out.println("Finishing order n."+ order.getID());

        for (Product product : productView.getItems()) {
            orderService.insertOrderProduct(order.getID(), product.getID(), product.getCount());
        }

        backPress();
    }

    @FXML
    public void backPress() {
        app.getMainController().changeScene(SceneType.TABLEVIEW);
        userName.setText("");
        selectedTable.setText("");
    }

    public Scene getScene() {
        return scene;
    }
}
