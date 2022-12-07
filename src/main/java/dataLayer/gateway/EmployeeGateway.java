package dataLayer.gateway;

import dataLayer.connection.SQLDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeGateway {

    /**
     * Return ResultSet from database by employee ID
     * @param employeeID employeID
     * @return resultSet
     */
    public static ResultSet getEmployeeByID(int employeeID) {
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
