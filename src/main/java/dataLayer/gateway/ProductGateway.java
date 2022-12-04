package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProductGateway {
    public static ResultSet insertProduct() {
        return null;
    }

    public static void removeProductByID() {

    }

    public static ResultSet getAllProducts() {
        String sql = "SELECT * FROM Product";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getProductByID(int productID) {
        String sql = "SELECT * FROM Product WHERE productID = ?";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, productID);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getProductsByTableID(int tableID) {
        String sql = "" +
                "SELECT p.productID, type, name, SUM(op.count) AS count, SUM(price) AS price " +
                "FROM TableOrders o " +
                "    JOIN OrderProducts op on o.orderID = op.orderID " +
                "    JOIN Product p on op.productID = p.productID " +
                "WHERE o.tableID = ? " +
                "GROUP BY p.productID, type, name";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, tableID);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getProductsByOrderID(int orderID) {
        String sql = "" +
                "SELECT p.productID, type, name, SUM(op.count) AS count, SUM(price) AS price " +
                "FROM TableOrders o " +
                "    JOIN OrderProducts op on o.orderID = op.orderID " +
                "    JOIN Product p on op.productID = p.productID " +
                "WHERE o.orderID = ? " +
                "GROUP BY p.productID, type, name";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, orderID);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
