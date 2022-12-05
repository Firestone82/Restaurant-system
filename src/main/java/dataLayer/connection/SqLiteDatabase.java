package dataLayer.connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqLiteDatabase implements IDatabase {
    private static String connectionString;
    private static File file;

    public SqLiteDatabase(String path) {
        this.file = new File("src/main/resources"+ path);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.connectionString = "jdbc:sqlite:" + file.getPath();
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
