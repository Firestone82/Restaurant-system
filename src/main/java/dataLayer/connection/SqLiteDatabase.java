package dataLayer.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqLiteDatabase implements IDatabase {
    private static String connectionString;

    public SqLiteDatabase(String path) {
        File file = new File("src/main/resources" + path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        connectionString = "jdbc:sqlite:" + file.getPath();
    }

    public static Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(connectionString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
