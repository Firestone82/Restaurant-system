package bussinessLayer.scripts;

import bussinessLayer.mapper.EmployeeMapper;
import bussinessLayer.objects.Employee;
import dataLayer.gateway.EmployeeGateway;
import javafx.util.Pair;

public class LoginScript {

    /**
     * Process login by database
     * @param employeeID employeeID
     * @param password password
     * @return if login was successful and employee
     */
    public Pair<Boolean, Employee> processLogin(String employeeID, String password) {
        int id = -1;

        try {
            id = Integer.parseInt(employeeID);
        } catch (Exception e) {
            return new Pair<>(false, null);
        }

        Employee employee = EmployeeMapper.map(EmployeeGateway.getEmployeeByID(id));
        if (employee != null) {
            String currentPass = employee.getPassword();
            String hashedPass = password.hashCode() + "";

            return new Pair<>(currentPass.equals(hashedPass), employee);
        }

        return new Pair<>(false, null);
    }
}
