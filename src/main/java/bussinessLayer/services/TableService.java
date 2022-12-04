package bussinessLayer.services;

import bussinessLayer.mapper.TableMapper;
import bussinessLayer.objects.Table;
import dataLayer.gateway.TableGateway;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableService {
    public void insertTable() {
        TableGateway.insertTable();
    }

    public void removeTable(int tableID) {
        TableGateway.removeByTableID(tableID);
    }

    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();

        try (ResultSet resultSet = TableGateway.getAllTables();) {
            while (resultSet.next()) {
                tables.add(TableMapper.map(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tables;
    }
}
