package bussinessLayer.mapper;

import bussinessLayer.objects.Order;

import java.sql.ResultSet;

public class OrderMapper {
    public static Order map(ResultSet rs) {
        try {
            return new Order(
                    rs.getInt("orderID"),
                    rs.getDate("created").toString(),
                    rs.getInt("employeeID"),
                    rs.getInt("paymentID")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
