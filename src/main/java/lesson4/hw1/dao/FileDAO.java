package lesson4.hw1.dao;

import lesson4.hw1.entity.File;
import lesson4.hw1.entity.Storage;
import lesson4.hw1.service.StorageServise;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";
    StorageServise storageServise = new StorageServise();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // если есть сторедж - сохранит в нём. если нету - сохранит в системе с дефолнтым ИД 999
    public void save(File file, Storage storage) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO file_ (ID, NAME, FORMAT, SIZE_, STORAGE_ID) VALUES (?,?,?,?,?)")) {

            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2, file.getName());
            preparedStatement.setString(3, file.getFormat());
            preparedStatement.setLong(4, file.getSize());
            if (storage != null)
                preparedStatement.setLong(5, storage.getId());
            else
                preparedStatement.setLong(5, 999);


            preparedStatement.executeUpdate();
            System.out.println("File id : " + file.getId() + "added in DAO");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // выборка всех файлов в заданном хранилище
    public List<File> getAllFilesInStorageById(long id) {
        List<File> allFilesInStorsgeByID = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String queryString = "SELECT * FROM FILE_ WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                allFilesInStorsgeByID.add(new File(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        storageServise.findStorageByID(resultSet.getLong(5))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allFilesInStorsgeByID;
    }

    public void delete(long id) {
    }

    public List<File> getAllFilesInSystem() {
        // выборка всех файлов в системе
        List<File> allFilesInSystem = new ArrayList<>();

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String queryString = "SELECT * FROM FILE_";
            ResultSet resultSet = statement.executeQuery(queryString);

            while (resultSet.next()) {
                allFilesInSystem.add(new File(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getLong(4),
                        storageServise.findStorageByID(resultSet.getLong(5))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allFilesInSystem;
    }


    public void update(File file, Storage storage) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE  FILE_ SET NAME = ?, FORMAT = ? , SIZE_ = ?, STORAGE_ID = ? WHERE ID = ?")) {

            preparedStatement.setString(1, file.getName());
            preparedStatement.setString(2, file.getFormat());
            preparedStatement.setLong(3, file.getSize());
            preparedStatement.setLong(4, storage.getId());
            preparedStatement.setLong(5, file.getId());

            preparedStatement.executeUpdate();
            System.out.println("File id : " + file.getId() + " was updated in DAO");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public File findByID(long id) {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            String queryString = "SELECT * FROM FILE_ WHERE ID = " + id;
            ResultSet resultSet1 = statement.executeQuery(queryString);

            return new File(resultSet1.getLong(1),
                    resultSet1.getString(2),
                    resultSet1.getString(3),
                    resultSet1.getLong(4),
                    storageServise.findStorageByID(resultSet1.getLong(5)));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



/*
    save(Object object)
    delete(long id)
    update(Object object)
    findById(long id)
*/
}
