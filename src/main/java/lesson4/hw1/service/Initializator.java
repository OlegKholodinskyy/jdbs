package lesson4.hw1.service;

import java.sql.*;

public class Initializator {

    private static long maxIDFile = 0;
    private static long maxIDStorage = 0;

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ххх";

    public static void initializeMaxIDs() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT MAX(ID) FROM FILE_");

            if (resultSet.getRow() == 1) {
                maxIDFile = resultSet.getLong(1);
                System.out.println("SELECT MAX(ID) FROM FILE_ " + maxIDFile);
            } else {
                maxIDFile = 1000;
            }
            ResultSet resultSet1 = statement.executeQuery("SELECT MAX(ID) FROM STORAGE");
            if (resultSet1.getRow() == 1) {
                maxIDFile = resultSet.getLong(1);
                System.out.println("SELECT MAX(ID) FROM STORAGE " + maxIDStorage);
            } else {
                maxIDStorage = 1000;
            }

        } catch (SQLException e) {
            System.out.println("Initializator went wrong");
            System.out.println(e.getMessage());
        }
    }

    public static long getMaxIDFile() {
        return maxIDFile;
    }

    public static long getMaxIDStorage() {
        return maxIDStorage;
    }
}
