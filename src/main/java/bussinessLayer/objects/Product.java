package bussinessLayer.objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product {
    public enum Type {
        FOOD,
        DRINK
    }

    private final SimpleIntegerProperty productID;
    private final Type type;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty count;
    private final SimpleDoubleProperty price;

    public Product(Integer productID, Type productType, String productName, Integer count, Double price) {
        this.productID = new SimpleIntegerProperty(productID);
        this.name = new SimpleStringProperty(productName);
        this.count = new SimpleIntegerProperty(count);
        this.price = new SimpleDoubleProperty(price);
        this.type = productType;
    }

    public int getID() {
        return productID.get();
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name.get();
    }

    public Integer getCount() {
        return count.get();
    }

    public void setCount(Integer count) {
        this.count.set(count);
    }

    public Double getTotal() {
        return price.get() * count.get();
    }
}
