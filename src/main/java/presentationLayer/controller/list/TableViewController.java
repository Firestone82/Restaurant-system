package presentationLayer.controller.list;

import bussinessLayer.objects.Employee;
import bussinessLayer.objects.Product;
import bussinessLayer.objects.Table;
import bussinessLayer.services.OrderService;
import bussinessLayer.services.ProductService;
import bussinessLayer.services.TableService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import presentationLayer.App;
import presentationLayer.controller.AbstractController;
import presentationLayer.enums.SceneType;

public class TableViewController extends AbstractController {
    @FXML Label selectedTable;
    @FXML Label userName;

    @FXML Button managerButton;

    @FXML TableView<Table> tableView;
    @FXML TableColumn<Table, String> tableName;
    @FXML TableColumn<Table, Boolean> tableState;

    @FXML TableView<Product> productView;
    @FXML TableColumn<Product, String> productName;
    @FXML TableColumn<Product, Integer> productCount;
    @FXML TableColumn<Product, Double> productTotal;

    public void initComponents() {
        this.tableName.setCellFactory(tc -> {
            TableCell<Table, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };

            cell.setOnMouseClicked(e -> {
                if (cell.getItem() != null) load(Integer.parseInt(cell.getItem().split("\\.")[1]));
            });

            return cell;
        });
        this.tableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.tableState.setCellValueFactory(new PropertyValueFactory<>("state"));

        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        this.productTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    public void loadValues(String... args) {
        this.userName.setText(app.getEmployee().toString());

        if (!selectedTable.getText().isEmpty()) {
            load(Integer.parseInt(selectedTable.getText().split("\\.")[1]));
        } else {
            load();
        }
    }

    public void load() {
        tableView.setItems(FXCollections.observableArrayList(app.getTableService().getAllTables()));
    }

    public void load(Integer tableID) {
        load();
        selectedTable.setText("Selected table n." + tableID);
        productView.setItems(FXCollections.observableArrayList(app.getProductService().getProductsFromTable(tableID)));
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

        app.getController().changeScene(SceneType.ADD, selectedTable.getText().split("\\.")[1]);
    }

    @FXML
    public void managerPress() {
        if (true || app.getEmployee().getPosition() == Employee.Type.MANAGER || app.getEmployee().getPosition() == Employee.Type.MAJITEL) {
            app.getController().changeScene(SceneType.EDIT);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You don't have permission to access this page");
            alert.showAndWait();
        }
    }

    @FXML
    public void addTablePress() {
        app.getTableService().insertTable();
        load();
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
        load();
    }

    @FXML
    public void logoutPress() {
        app.getController().changeScene(SceneType.LOGIN);
        userName.setText("");
    }
}
