package dataLayer.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLDatabase implements IDatabase {
    private static final String connectionString =
            "jdbc:sqlserver://dbsys.cs.vsb.cz\\sqldb;" +
                    "databaseName=MIK0486;" +
                    "user=MIK0486;" +
                    "password=w97mfTR160Z2B1qh;" +
                    "encrypt=false;" +
                    "trustServerCertificate=false;" +
                    "loginTimeout=30;";

    public SQLDatabase() {
        System.out.println("Connecting to database...");

        try (Connection connection = getConnection()) {
            System.out.println(" - Connection Successful");
        } catch (Exception e) {
            System.out.println(" - Connection Failed");
            System.out.println(" - Error: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
