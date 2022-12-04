package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeGateway {
    public static ResultSet getEmployeeByID(Integer employeeID) {
        String sql = "SELECT * FROM Employee WHERE employeeID = ?";

        try {
            Connection connection = SQLDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, employeeID);
            return statement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
