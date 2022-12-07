package dataLayer.unitOfWork;

import dataLayer.connection.IDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class OrderProductUOF {
    private final int orderID;
    private final List<String> update = new ArrayList<>();
    private final List<String> delete = new ArrayList<>();
    private final List<String> insert = new ArrayList<>();

    public OrderProductUOF(int orderID) {
        this.orderID = orderID;
    }

    public void update(int productID, int count) {
        update.add(productID + "@" + count);
    }

    public void delete(int productID) {
        delete.add(productID +"");
    }

    public void add(int productID, int count) {
        insert.add(productID + "@" + count);
    }

    public void commit() {
        try (Connection connection = IDatabase.getConnection()) {
            if (!insert.isEmpty()) {
                String insertOrderSQL = "INSERT INTO OrderProducts (orderID, productID, count) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(insertOrderSQL)) {
                    for (String in : insert) {
                        String[] inArgs = in.split("@");

                        statement.setInt(1, orderID);
                        statement.setInt(2, Integer.parseInt(inArgs[0]));
                        statement.setInt(3, Integer.parseInt(inArgs[1]));
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }

                String updateProductSQL = "UPDATE Product SET count = count - ? WHERE productID = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateProductSQL)) {
                    for (String in : insert) {
                        String[] inArgs = in.split("@");

                        statement.setInt(1, Integer.parseInt(inArgs[1]));
                        statement.setInt(2, Integer.parseInt(inArgs[0]));
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }
            }

            if (!update.isEmpty()) {
                String updateOrderSQL = "UPDATE OrderProducts SET count = ? WHERE orderID = ? AND productID = ?";
                try (PreparedStatement statement = connection.prepareStatement(updateOrderSQL)) {
                    for (String in : update) {
                        String[] inArgs = in.split("@");

                        statement.setInt(1, Integer.parseInt(inArgs[1]));
                        statement.setInt(2, orderID);
                        statement.setInt(3, Integer.parseInt(inArgs[0]));
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }
            }

            if (!delete.isEmpty()) {
                String deleteOrderSQL = "DELETE FROM OrderProducts WHERE orderID = ? AND productID = ?";
                try (PreparedStatement statement = connection.prepareStatement(deleteOrderSQL)) {
                    for (String in : delete) {
                        statement.setInt(1, orderID);
                        statement.setInt(2, Integer.parseInt(in));
                        statement.addBatch();
                    }

                    statement.executeBatch();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
