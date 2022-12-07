package bussinessLayer.mapper;

import bussinessLayer.objects.Table;

import java.sql.ResultSet;

public class TableMapper {

    /**
     * Construct object by result set
     * @param rs ResultSet
     * @return Table Object
     */
    public static Table map(ResultSet rs) {
        try {
            return new Table(
                    rs.getInt("tableID"),
                    rs.getBoolean("reserved")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
