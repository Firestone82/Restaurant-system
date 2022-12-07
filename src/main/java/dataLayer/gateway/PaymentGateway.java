package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PaymentGateway {

    public static boolean createPayment(int type, double total, int employeeID) {
        String sql = "INSERT INTO Payment (type, total, created, employeeID, successful) VALUES (?,?,CURRENT_TIMESTAMP,?,1)";

        try (Connection connection = SQLDatabase.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, type);
                statement.setDouble(2, total);
                statement.setInt(3, employeeID);
                return statement.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
