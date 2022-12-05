package bussinessLayer.services;

import bussinessLayer.mapper.ProductMapper;
import bussinessLayer.objects.Product;
import dataLayer.gateway.ProductGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public List<Product> getProductsFromTable(Integer tableID) {
        List<Product> products = new ArrayList<>();

        try (ResultSet resultSet = ProductGateway.getProductsByTableID(tableID)) {
            while (resultSet.next()) {
                products.add(ProductMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public void insertProductStock(int productID, int count) {
        ProductGateway.increaseProductIDStock(productID, count);
    }

    public void setProductStock(int productID, int count) {
        ProductGateway.setProductIDStock(productID, count);
    }

    public void removeProductStock(int productID, int count) {
        ProductGateway.decreaseProductIDStock(productID, count);
    }

    public List<Product> getProductsFromOrder(Integer orderID) {
        List<Product> products = new ArrayList<>();

        try (ResultSet resultSet = ProductGateway.getProductsByOrderID(orderID)) {
            while (resultSet.next()) {
                products.add(ProductMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try (ResultSet resultSet = ProductGateway.getAllProducts()) {
            while (resultSet.next()) {
                products.add(ProductMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
