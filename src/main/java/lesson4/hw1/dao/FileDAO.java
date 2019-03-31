package lesson4.hw1.dao;

import lesson4.hw1.entity.File;
import lesson4.hw1.entity.Storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class FileDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "ххх";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public void save (File file, Storage storage) {
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO file_ (ID, NAME, FORMAT, SIZE_, STORAGE_ID) VALUES (?,?,?,?,?)")){

            preparedStatement.setLong(1, file.getId());
            preparedStatement.setString(2,file.getName());
            preparedStatement.setString(3,file.getFormat());
            preparedStatement.setLong(4,file.getSize());
            if (storage != null)
                preparedStatement.setLong(5, storage.getId());
            else
                preparedStatement.setLong(5, 0);


            preparedStatement.executeUpdate();
            System.out.println("File id : " + file.getId() + "added in DAO");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public List<File> getAllFilesInStorageById(long id) {
        return null;
    }

    public void delete(long id) {
    }
/*
    save(Object object)
    delete(long id)
    update(Object object)
    findById(long id)
*/
}
