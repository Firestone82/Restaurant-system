package bussinessLayer.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Table {
    private final SimpleIntegerProperty tableID;
    private final SimpleBooleanProperty active;

    public Table(Integer tableID, boolean active) {
        this.tableID = new SimpleIntegerProperty(tableID);
        this.active = new SimpleBooleanProperty(active);
    }

    public Integer getTableID() {
        return tableID.get();
    }

    public String getName() {
        return "Table n." + tableID.get();
    }

    public String getState() {
        return active.get() ? "Yes" : "---";
    }
}
