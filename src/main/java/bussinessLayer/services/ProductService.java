package bussinessLayer.services;

import bussinessLayer.mapper.ProductMapper;
import bussinessLayer.objects.Product;
import dataLayer.gateway.ProductGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

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

    /**
     * Get all products from specific tableID
     * @param tableID table ID
     * @return list of products
     */
    public List<Product> getProductsFromTable(int tableID) {
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

    /**
     * Insert product number into database
     * @param productID productID
     * @param count count
     */
    public void insertProductStock(int productID, int count) {
        ProductGateway.increaseProductIDStock(productID, count);
    }

    /**
     * Set product number into database
     * @param productID productID
     * @param count count
     */
    public void setProductStock(int productID, int count) {
        ProductGateway.setProductIDStock(productID, count);
    }

    /**
     * Remove product number from database
     * @param productID productID
     * @param count count
     */
    public void removeProductStock(int productID, int count) {
        ProductGateway.decreaseProductIDStock(productID, count);
    }

    /**
     * Return products from orderID
     * @return list of products
     */
    public List<Product> getProductsFromOrder(int orderID) {
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
}
