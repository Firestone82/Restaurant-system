package presentationLayer.controller.list;

import bussinessLayer.objects.Product;
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

public class ProductViewController {
    private App app;
    private Pane pane;
    private Scene scene;

    private @FXML Label userName;

    private @FXML ComboBox<Product> productAddName;
    private @FXML Spinner<Integer> productAddCount;

    private @FXML TableView<Product> productView;
    private @FXML TableColumn<Product, String> productName;
    private @FXML TableColumn<Product, String> productType;
    private @FXML TableColumn<Product, Integer> productCount;

    private final ProductService orderService = new ProductService();

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;
        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());

        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        load();
    }

    public void loadValues(String userName) {
        this.userName.setText(userName);
        load();
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

    }

    @FXML
    public void removePress() {

    }

    @FXML
    public void finishPress() {
//        System.out.println("Finishing order n."+ order.getID());
//
//        for (Product product : productView.getItems()) {
//            orderService.insertOrderProduct(order.getID(), product.getID(), product.getCount());
//        }
//
//        backPress();
    }

    @FXML
    public void backPress() {
//        app.getMainController().changeScene(SceneType.TABLEVIEW);
//        userName.setText("");
//        selectedTable.setText("");
    }

    public Scene getScene() {
        return scene;
    }
}
