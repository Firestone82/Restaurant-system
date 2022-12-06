package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;
import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderGateway {

    public static ResultSet insertTableOrder(int employeeID, int tableID) {
        String sql = "INSERT INTO [Order] (created, employeeID) VALUES (CURRENT_TIMESTAMP, ?)";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, employeeID);
            statement.executeUpdate();

            return statement.getGeneratedKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void assignOrderProduct(int orderID, int productID, int count) {
        String sql = "INSERT INTO OrderProducts (orderID, productID, count) VALUES (?, ?, ?)";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, orderID);
                statement.setInt(2, productID);
                statement.setInt(3, count);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeOrderProduct(int orderID, int productID, int count) {
        String sql = "UPDATE OrderProducts SET count = ? WHERE orderID = ? AND productID = ?";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, count);
                statement.setInt(2, orderID);
                statement.setInt(3, productID);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteOrderProduct(int orderID, int productID) {
        String sql = "DELETE FROM OrderProducts WHERE orderID = ? AND productID = ?";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, orderID);
                statement.setInt(2, productID);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ResultSet getOrderByOrderID(int orderID) {
        String sql = "SELECT * FROM [Order] WHERE orderID = ?";

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

    public static ResultSet getOrdersByTableID(int tableID) {
        String sql = "" +
                "SELECT tableID, t.orderID, created, employeeID, paymentID " +
                "FROM TableOrders t " +
                "   JOIN [Order] o ON o.orderID = t.orderID " +
                "WHERE tableID = ?";

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
}
