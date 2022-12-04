package presentationLayer.controller.list;

import bussinessLayer.objects.Order;
import bussinessLayer.objects.Product;
import bussinessLayer.objects.Table;
import bussinessLayer.services.OrderService;
import bussinessLayer.services.ProductService;
import bussinessLayer.services.TableService;
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

import static java.lang.Thread.sleep;

public class TableViewController {
    private App app;
    private Pane pane;
    private Scene scene;

    @FXML Label selectedTable;
    @FXML Label userName;

    @FXML TableView<Table> tableView;
    @FXML TableColumn<Table, String> tableName;
    @FXML TableColumn<Table, Boolean> tableState;

    @FXML TableView<Product> productView;
    @FXML TableColumn<Product, String> productName;
    @FXML TableColumn<Product, Integer> productCount;
    @FXML TableColumn<Product, Double> productTotal;

    private final TableService tableService = new TableService();
    private final OrderService orderService = new OrderService();
    private final ProductService productService = new ProductService();

    public void init(App app, Pane pane) {
        this.app = app;
        this.pane = pane;
        this.scene = new Scene(pane);
        this.scene.getStylesheets().add(getClass().getResource("/assets/application.css").toExternalForm());

        this.tableName.setCellFactory(tc -> {
            TableCell<Table, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };

            cell.setOnMouseClicked(e -> {
                if (cell.getItem() == null) return;

                selectedTable.setText("Loading table ...");
                System.out.println("Loading " + cell.getItem() + "...");

                loadTable(Integer.parseInt(cell.getItem().split("\\.")[1]));
            });

            return cell;
        });
        this.tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tableState.setCellValueFactory(new PropertyValueFactory<>("state"));

        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadTables();
        loadValues("Administrator");
    }

    public void loadValues(String userName) {
        this.userName.setText(userName);

        if (!selectedTable.getText().isEmpty()) {
            loadTable(Integer.parseInt(selectedTable.getText().split("\\.")[1]));
        }
    }

    public void loadTables() {
        tableView.setItems(FXCollections.observableArrayList(new TableService().getAllTables()));
    }

    public void loadTable(Integer tableID) {
        selectedTable.setText("Products ordered on table n." + tableID);
        productView.setItems(FXCollections.observableArrayList(productService.getProductsFromTable(tableID)));
    }

    @FXML
    public void payPress() {

    }

    @FXML
    public void addPress() {
        if (selectedTable.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No table selected");
            alert.showAndWait();
            return;
        }

        app.getMainController().changeScene(SceneType.ADD, selectedTable.getText().split("\\.")[1]);
    }

    @FXML
    public void managerPress() {

    }

    @FXML
    public void addTablePress() {
        tableService.insertTable();
        loadTables();
    }

    @FXML
    public void deleteTablePress() {
        if (selectedTable.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No table selected");
            alert.showAndWait();
            return;
        }

        new TableService().removeTable(Integer.parseInt(selectedTable.getText().split("\\.")[1]));
        productView.setItems(FXCollections.observableArrayList());
        selectedTable.setText("");
        loadTables();
    }

    @FXML
    public void logoutPress() {
        app.getMainController().changeScene(SceneType.LOGIN);
        app.getMainController().setEmployee(null);
        userName.setText("");
    }

    public Scene getScene() {
        return scene;
    }
}
