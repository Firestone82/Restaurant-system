package bussinessLayer.services;

import bussinessLayer.mapper.OrderMapper;
import bussinessLayer.objects.Order;
import dataLayer.gateway.OrderGateway;
import dataLayer.gateway.TableGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public Order insertOrder(Integer tableID, Integer employeeID) {
        try (ResultSet rs = OrderGateway.insertTableOrder(employeeID, tableID)) {

            if (rs.next()) {
                int orderID = rs.getInt(1);

                TableGateway.assignTableOrder(tableID, orderID);
                try (ResultSet rs2 = OrderGateway.getOrderByOrderID(orderID)) {
                    if (rs2.next()) {
                        return OrderMapper.map(rs2);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void insertOrderProduct(Integer orderID, Integer productID, Integer quantity) {
        OrderGateway.assignOrderProduct(orderID, productID, quantity);
    }

    public List<Order> getTableOrders(Integer tableID) {
        List<Order> orders = new ArrayList<>();

        try (ResultSet resultSet = OrderGateway.getOrdersByTableID(tableID)) {
            while (resultSet.next()) {
                orders.add(OrderMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }
}
