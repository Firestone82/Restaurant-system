package dataLayer.unitOfWork;

import dataLayer.connection.SQLDatabase;

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

    public void add(int productID, int count) {
        insert.add(productID +"@"+ count);
    }

    public void commit() {
        String insertSQL = "INSERT INTO OrderProducts (orderID, productID, count) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE Product SET count = count - ? WHERE productID = ?";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                for (String in : insert) {
                    String[] inArgs = in.split("@");

                    statement.setInt(1, orderID);
                    statement.setInt(2, Integer.parseInt(inArgs[0]));
                    statement.setInt(3, Integer.parseInt(inArgs[1]));
                    statement.addBatch();
                }

                statement.executeBatch();
            }

            try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
                for (String in : insert) {
                    String[] inArgs = in.split("@");

                    statement.setInt(1, Integer.parseInt(inArgs[1]));
                    statement.setInt(2, Integer.parseInt(inArgs[0]));
                    statement.addBatch();
                }

                statement.executeBatch();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
