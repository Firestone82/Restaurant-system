package bussinessLayer.services;

import bussinessLayer.mapper.TableMapper;
import bussinessLayer.objects.Table;
import dataLayer.gateway.TableGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableService {

    /**
     * Insert empty table into database
     */
    public void insertTable() {
        TableGateway.insertTable();
    }

    /**
     * Remove table from database by tableID
     * @param tableID
     */
    public void removeTable(int tableID) {
        TableGateway.removeByTableID(tableID);
    }

    /**
     * Get all tables from database
     * @return list of tables
     */
    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();

        try (ResultSet resultSet = TableGateway.getAllTables()) {
            while (resultSet.next()) {
                tables.add(TableMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables;
    }
}
