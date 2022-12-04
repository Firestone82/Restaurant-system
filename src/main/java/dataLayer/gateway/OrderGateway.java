package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrderGateway {

    public static ResultSet insertTableOrder(Integer employeeID, Integer tableID) {
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

    public static void assignOrderProduct(int orderID, int productID, Integer count) {
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

    public static ResultSet getOrderByOrderID(Integer orderID) {
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

    public static ResultSet getOrdersByTableID(Integer tableID) {
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
