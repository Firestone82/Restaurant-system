module lab {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;

    opens bussinessLayer.objects to javafx.fxml, javafx.base;
    opens dataLayer.connection to javafx.fxml, javafx.base;
    opens presentationLayer to javafx.fxml, javafx.base;
    opens presentationLayer.enums to javafx.fxml, javafx.base;
    opens presentationLayer.controller to javafx.fxml, javafx.base;
    opens presentationLayer.controller.list to javafx.fxml, javafx.base;
    exports presentationLayer;
}
