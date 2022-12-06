package presentationLayer.enums;

public enum SceneType {
    LOGIN("/scenes/Login.fxml", "Login"),
    TABLEVIEW("/scenes/TableView.fxml", "TableView"),
    ADD("/scenes/TableOrders.fxml", "Add"),
    EDIT("/scenes/ProductView.fxml", "Edit"),
    PAY("/scenes/OrderPayment.fxml", "Pay");

    private final String fxml;
    private final String title;

    SceneType(String fxml, String title) {
        this.fxml = fxml;
        this.title = title;
    }

    public String getFXML() {
        return fxml;
    }

    public String getTitle() {
        return title;
    }
}
