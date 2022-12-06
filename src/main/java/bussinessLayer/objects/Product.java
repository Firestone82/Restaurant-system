package bussinessLayer.objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Product implements Cloneable {
    private final SimpleIntegerProperty productID;
    private final Type type;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty count;
    private final SimpleDoubleProperty price;

    public Product(int productID, Type productType, String productName, int count, double price) {
        this.productID = new SimpleIntegerProperty(productID);
        this.name = new SimpleStringProperty(productName);
        this.count = new SimpleIntegerProperty(count);
        this.price = new SimpleDoubleProperty(price);
        this.type = productType;
    }

    public Product(Product cloneProduct) {
        this.productID = new SimpleIntegerProperty(cloneProduct.getID());
        this.name = new SimpleStringProperty(cloneProduct.getName());
        this.count = new SimpleIntegerProperty(cloneProduct.getCount());
        this.price = new SimpleDoubleProperty(cloneProduct.getPrice());
        this.type = cloneProduct.getType();
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

    public int getCount() {
        return count.get();
    }

    public double getPrice() {
        return price.get();
    }

    public void setCount(int count) {
        this.count.set(count);
    }

    public double getTotal() {
        return price.get() * count.get();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public enum Type {
        FOOD,
        DRINK
    }
}
