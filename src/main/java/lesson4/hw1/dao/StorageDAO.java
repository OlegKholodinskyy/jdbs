package lesson4.hw1.dao;

import lesson4.hw1.entity.Storage;
import lesson4.hw1.service.StorageServise;

import java.sql.*;

public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";
    StorageServise storageServise = new StorageServise();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public Storage findByID(long id) {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String queryString = "SELECT * FROM STORAGE WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(queryString);

            return new Storage(resultSet.getLong(1),
                    makeArrayOfAllowedFormats(resultSet.getString(2)),
                    resultSet.getString(3),
                    resultSet.getLong(4));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String[] makeArrayOfAllowedFormats(String string) {
        return string.split(", ");
    }
    /*
save(Object object)
delete(long id)
update(Object object)
findById(long id)

     */
}
