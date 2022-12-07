package presentationLayer.controller.list;

import bussinessLayer.objects.Product;
import bussinessLayer.services.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import presentationLayer.controller.AbstractController;
import presentationLayer.enums.SceneType;

import java.util.Optional;

public class ProductViewController extends AbstractController {
    private @FXML Label userName;

    private @FXML ComboBox<Product> productAddName;
    private @FXML Spinner<Integer> productAddCount;

    private @FXML TableView<Product> productView;
    private @FXML TableColumn<Product, String> productName;
    private @FXML TableColumn<Product, String> productType;
    private @FXML TableColumn<Product, Integer> productCount;

    public void initComponents() {
        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productType.setCellValueFactory(new PropertyValueFactory<>("type"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));

        productAddCount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
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
        load();
    }

    public void load() {
        productAddName.setItems(FXCollections.observableArrayList(ProductService.getAllProducts()));
        productView.setItems(FXCollections.observableArrayList(ProductService.getAllProducts()));
    }

    @FXML
    public void addPress() {
        Product selectedProduct = productAddName.getItems().stream().filter(p -> p.getName().equals(productAddName.getValue().getName())).findFirst().get();
        int selectedCount = productAddCount.getValue();

        app.getProductService().insertProductStock(selectedProduct.getID(), selectedCount);

        load();
    }

    @FXML
    public void removePress() {
        Product selectedProduct = productAddName.getItems().stream().filter(p -> p.getName().equals(productAddName.getValue().getName())).findFirst().get();
        int selectedCount = productAddCount.getValue();

        if (selectedProduct.getCount() - selectedCount < 0) {
            ButtonType yes = new ButtonType("Override", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(
                    Alert.AlertType.ERROR,
                    "Trying to remove more stock then there is? \n Number will be set to 0.", no, yes);
            alert.setHeaderText("Negative stock number!");
            alert.setTitle("Override confirmation");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == yes) {
                app.getProductService().setProductStock(selectedProduct.getID(), 0);
            }
        } else {
            app.getProductService().removeProductStock(selectedProduct.getID(), selectedCount);
        }

        load();
    }

    @FXML
    public void backPress() {
        app.getController().changeScene(SceneType.TABLEVIEW);
        userName.setText("");
    }
}
