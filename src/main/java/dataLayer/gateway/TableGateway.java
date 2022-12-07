package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TableGateway {

    /**
     * Insert empty table to database
     */
    public static void insertTable() {
        String sql = "INSERT INTO [Table] (reserved, occupied) VALUES (0,0)";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Assign order to table
     * @param tableID tableID
     * @param orderID orderID
     */
    public static void assignTableOrder(int tableID, int orderID) {
        String sql = "INSERT INTO TableOrders (tableID, orderID) VALUES (?, ?)";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, tableID);
                statement.setInt(2, orderID);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove table by table ID
     * @param tableID tableID
     */
    public static void removeByTableID(int tableID) {
        String sql = "DELETE FROM [Table] WHERE tableID = ?";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, tableID);
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get table by ID
     * @param tableID tableiD
     * @return resultSet
     */
    public static ResultSet getTableByID(int tableID) {
        String sql = "SELECT * FROM [Table] WHERE tableID = ?";

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

    /**
     * Get all tables from database
     * @return resultSet
     */
    public static ResultSet getAllTables() {
        String sql = "SELECT * FROM [Table]";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
