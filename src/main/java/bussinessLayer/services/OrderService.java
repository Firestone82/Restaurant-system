package bussinessLayer.services;

import bussinessLayer.mapper.OrderMapper;
import bussinessLayer.objects.Order;
import dataLayer.gateway.OrderGateway;
import dataLayer.gateway.TableGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    public Order insertOrder(int tableID, int employeeID) {
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

    public void insertOrderProduct(int orderID, int productID, int quantity) {
        OrderGateway.assignOrderProduct(orderID, productID, quantity);
    }

    public void removeOrderProduct(int orderID, int productID, int quantity) {
        OrderGateway.removeOrderProduct(orderID, productID, quantity);
    }

    public void deleteOrderProduct(int orderID, int productID) {
        OrderGateway.deleteOrderProduct(orderID, productID);
    }

    public List<Order> getTableOrders(int tableID) {
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
