package bussinessLayer.mapper;

import bussinessLayer.objects.Table;

import java.sql.ResultSet;

public class TableMapper {
    public static Table map(ResultSet rs) {
        try {
            return new Table(
                    rs.getInt("tableID"),
                    rs.getBoolean("occupied")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
