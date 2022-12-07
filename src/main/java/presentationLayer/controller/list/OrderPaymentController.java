package presentationLayer.controller.list;

import bussinessLayer.objects.Payment;
import bussinessLayer.objects.Product;
import bussinessLayer.services.TableService;
import dataLayer.unitOfWork.OrderProductUOF;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentationLayer.controller.AbstractController;
import presentationLayer.enums.SceneType;

import java.util.List;
import java.util.Optional;

public class OrderPaymentController extends AbstractController {
    private @FXML Label userName;
    private @FXML Label selectedTable;

    private @FXML TableView<Product> tableOrderView;
    private @FXML TableColumn<Product, String> productViewName;
    private @FXML TableColumn<Product, Integer> productViewCount;
    private @FXML TableColumn<Product, Integer> productViewTotal;

    private @FXML TableView<Product> tablePayView;
    private @FXML TableColumn<Product, String> productPayName;
    private @FXML TableColumn<Product, Integer> productPayCount;
    private @FXML TableColumn<Product, Integer> productPayTotal;

    public void initComponents() {
        this.productViewName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productViewCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productViewTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        this.productPayName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productPayCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productPayTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        this.tableOrderView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    if (event.getClickCount() == 3 || event.isShiftDown()) {
                        transfare(tableOrderView, tablePayView, row.getItem(), -1);
                    } else if (event.getClickCount() == 1) {
                        transfare(tableOrderView, tablePayView, row.getItem(), 1);
                    }
                }
            });

            return row;
        });

        this.tablePayView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty()) {
                    if (event.getClickCount() == 3 || event.isShiftDown()) {
                        transfare(tablePayView, tableOrderView, row.getItem(), -1);
                    } else if (event.getClickCount() == 1) {
                        transfare(tablePayView, tableOrderView, row.getItem(), 1);
                    }
                }
            });

            return row;
        });
    }

    public void transfare(TableView<Product> from, TableView<Product> to, Product product, int count) {
        Product payProduct = to.getItems().stream().filter(p -> p.getID() == product.getID()).findFirst().orElse(null);

        if (count == -1) {
            if (payProduct == null) {
                Product cloneProduct = new Product(product);
                to.getItems().add(cloneProduct);
            } else {
                payProduct.setCount(payProduct.getCount() + product.getCount());
            }

            from.getItems().remove(product);
        } else {
            if (payProduct == null) {
                Product cloneProduct = new Product(product);
                cloneProduct.setCount(1);
                to.getItems().add(cloneProduct);
            } else {
                payProduct.setCount(payProduct.getCount() + 1);
            }

            if (product.getCount() == 1) {
                from.getItems().remove(product);
            } else {
                product.setCount(product.getCount() - 1);
            }
        }

        tableOrderView.refresh();
        tablePayView.refresh();
    }

    public void loadValues(String... args) {
        this.userName.setText(app.getEmployee().toString());
        this.selectedTable.setText(args[0]);
        this.tablePayView.getItems().clear();
        this.tableOrderView.getItems().clear();
        load();
    }

    public void load() {
        tableOrderView.setItems(FXCollections.observableArrayList(app.getProductService().getProductsFromTable(Integer.parseInt(selectedTable.getText().split("\\.")[1]))));
    }

    @FXML
    public void payPress() {
        double total = 0D;

        for (Product product : tablePayView.getItems()) {
            total += product.getTotal();
        }

        ButtonType card = new ButtonType("Card", ButtonBar.ButtonData.OK_DONE);
        ButtonType cash = new ButtonType("Cash", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Choose the type of payment customer wants.", card, cash, cancel);
        alert.setHeaderText("Payment type");
        alert.setTitle("Override confirmation");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(cash) == card) {
            app.getPaymentService().insertPayment(Payment.Type.CARD, total, app.getEmployee().getEmployeeID());
        } else {
            app.getPaymentService().insertPayment(Payment.Type.CASH, total, app.getEmployee().getEmployeeID());
        }

        tablePayView.getItems().clear();

        if (tableOrderView.getItems().size() == 0) {
            app.getTableService().removeTable(Integer.parseInt(selectedTable.getText().split("\\.")[1]));
        }
    }

    @FXML
    public void selectPress() {
        for (Product product : List.copyOf(tableOrderView.getItems())) {
            transfare(tableOrderView, tablePayView, product, -1);
        }
    }

    @FXML
    public void unselectPress() {
        for (Product product : List.copyOf(tablePayView.getItems())) {
            transfare(tablePayView, tableOrderView, product, -1);
        }
    }

    @FXML
    public void backPress() {
        app.getController().changeScene(SceneType.TABLEVIEW);
        userName.setText("");
    }
}
