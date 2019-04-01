package lesson4.hw1.dao;

import lesson4.hw1.entity.Storage;
import lesson4.hw1.service.StorageServise;

import java.sql.*;

public class StorageDAO {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";
//    StorageServise storageServise = new StorageServise();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public Storage findByID(long id) {
        Storage storage;
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String queryString = "SELECT * FROM STORAGE WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(queryString);


            if (resultSet.next()) {
                storage = new Storage(resultSet.getLong(1),
                        makeArrayOfAllowedFormats(resultSet.getString(2)),
                        resultSet.getString(3),
                        resultSet.getLong(4));
                return storage;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String[] makeArrayOfAllowedFormats(String stringFormats) {
        String cutString = stringFormats.substring(1, stringFormats.length()-1);
        String[] arr = cutString.split("\",\"");

       return arr;
    }


    public Storage save(Storage storage) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STORAGE (ID, FORMATS_SUPPORTED, STORAGE_COUNTRY, STORAGE_MAX_SIZE) VALUES (?,?,?,?)")) {

            preparedStatement.setLong(1, storage.getId());
            preparedStatement.setString(2, makeStringFormatsSupported(storage.getFormatsSupported()));
            preparedStatement.setString(3, storage.getStorageCountry());
            preparedStatement.setLong(4, storage.getStorageMaxSize());

            preparedStatement.executeUpdate();
            System.out.println("Storage id : " + storage.getId() + "added in DAO");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return storage;
    }

    private String makeStringFormatsSupported(String[] formatsSupported) {
        StringBuffer stringBuffer = new StringBuffer("\"");

        for (String s : formatsSupported){
            stringBuffer.append(s).append("\"" + "," + "\"");        }
        return stringBuffer.toString().substring(0,(stringBuffer.length()-2));
    }

    public Long delete(long id) {

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String queryString = "DELETE * FROM STORAGE WHERE ID = " + id;
            statement.executeUpdate(queryString);
            System.out.println("Storage id : " + id + " was deleted.");
            return id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void update(Storage storage) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  STORAGE SET FORMATS_SUPPORTED = ?, STORAGE_COUNTRY = ? , STORAGE_MAX_SIZE = ?  WHERE ID = ?")) {

            preparedStatement.setString(1, storage.getFormatsSupported().toString());
            preparedStatement.setString(2, storage.getStorageCountry());
            preparedStatement.setLong(3, storage.getStorageMaxSize());
            preparedStatement.setLong(4, storage.getId());
            preparedStatement.executeUpdate();
            System.out.println("Storage id : " + storage.getId() + " was updated in DAO");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    /*
save(Object object)
delete(long id)
update(Object object)
findById(long id)

     */
}
