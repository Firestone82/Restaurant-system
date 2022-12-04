package bussinessLayer.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Order {
    private final SimpleIntegerProperty orderID;
    private final SimpleStringProperty created;
    private final SimpleIntegerProperty employeeID;
    private final SimpleIntegerProperty paymentID;

    public Order(Integer orderID, String created, Integer employeeID, Integer paymentID) {
        this.orderID = new SimpleIntegerProperty(orderID);
        this.created = new SimpleStringProperty(created);
        this.employeeID = new SimpleIntegerProperty(employeeID);
        this.paymentID = new SimpleIntegerProperty(paymentID);
    }

    public Integer getID() {
        return orderID.get();
    }

    public String getCreated() {
        return created.get();
    }

    public int getEmployeeID() {
        return employeeID.get();
    }

    public int getPaymentID() {
        return paymentID.get();
    }
}
