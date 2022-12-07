package bussinessLayer.mapper;

import bussinessLayer.objects.Employee;

import java.sql.ResultSet;

public class EmployeeMapper {

    /**
     * Construct object by result set
     * @param rs ResultSet
     * @return Table Object
     */
    public static Employee map(ResultSet rs) {
        try {
            if (rs.next()) {
                return new Employee(
                        rs.getInt("employeeID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDate("birthDate").toString(),
                        rs.getDate("start").toString(),
                        rs.getDate("end") == null ? null : rs.getDate("end").toString(),
                        Employee.Type.valueOf(rs.getString("position").toUpperCase()),
                        rs.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
