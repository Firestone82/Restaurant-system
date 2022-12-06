package presentationLayer.controller.list;

import bussinessLayer.objects.Order;
import bussinessLayer.objects.Product;
import bussinessLayer.services.OrderService;
import bussinessLayer.services.ProductService;
import dataLayer.unitOfWork.OrderProductUOF;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentationLayer.controller.AbstractController;
import presentationLayer.enums.SceneType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TableOrdersController extends AbstractController {
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

    public void initComponents() {
        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        productAddCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,500,1));
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
    }

    public void loadValues(String... args) {
        this.userName.setText(app.getEmployee().toString());
        this.selectedTable.setText(args[0]);

        this.productView.getItems().clear();
        this.productAddCount.decrement(Integer.MAX_VALUE);
        this.productAddName.getSelectionModel().clearSelection();
        this.order = orderService.insertOrder(Integer.parseInt(args[0].split("\\.")[1]), app.getEmployee().getEmployeeID());

        load();
    }

    public void load() {
        productAddName.setItems(FXCollections.observableArrayList(ProductService.getAllProducts()));
        productView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
    }

    @FXML
    public void addPress() {
        Product selectedProduct = productAddName.getItems().stream().filter(p -> p.getName().equals(productAddName.getValue().getName())).findFirst().get();
        int selectedCount = productAddCount.getValue();

        if (selectedProduct.getCount() - selectedCount < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not enough products");
            alert.setContentText("There are only " + selectedProduct.getCount() + "x " + selectedProduct.getName() + " left in stock");
            alert.showAndWait();
            return;
        } else {
            selectedProduct.setCount(selectedProduct.getCount() - selectedCount);
        }

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
        OrderProductUOF orderProductUOF = new OrderProductUOF(order.getID());

        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No!", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(
                Alert.AlertType.WARNING,
                "Did you verify if order is correct?", no, yes);
        alert.setHeaderText("Verification"+ selectedTable.getText().split("\\.")[1]);
        alert.setTitle("Order Confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(no) == yes) {
            for (Product product : productView.getItems()) {
                orderProductUOF.add(product.getID(), product.getCount());
            }

            orderProductUOF.commit();
            backPress();
        }
    }

    @FXML
    public void backPress() {
        app.getController().changeScene(SceneType.TABLEVIEW);
        userName.setText("");
        selectedTable.setText("");
    }
}
