package bussinessLayer.mapper;

import bussinessLayer.objects.Product;

import java.sql.ResultSet;

public class ProductMapper {
    public static Product map(ResultSet rs) {
        try {
            return new Product(
                    rs.getInt("productID"),
                    rs.getInt("type") == 0 ? Product.Type.FOOD : Product.Type.DRINK,
                    rs.getString("name"),
                    rs.getInt("count"),
                    rs.getDouble("price")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
