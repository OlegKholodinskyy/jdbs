package lesson4;

import lesson4.Exception.BadSavingException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TransactionDemo {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    public static void save(List<Product> products) {
        try (Connection connection = getConnection()) {

            saveList(products, connection);

        } catch (BadSavingException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void saveList(List<Product> products, Connection connection) throws SQLException, BadSavingException {

        long IDProductCurentllySaving = 0;
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?,?,?,?)")) {

            connection.setAutoCommit(false);
            for (Product p : products) {
                IDProductCurentllySaving = p.getId();
                ps.setLong(1, p.getId());
                ps.setString(2, p.getName());
                ps.setString(3, p.getDescription());
                ps.setInt(4, p.getPrice());

                int res = ps.executeUpdate();
                System.out.println("Save was finished with result " + res + ". ID product :" + p.getId());
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new BadSavingException("Product with ID : " + IDProductCurentllySaving + " can not be saved into Data Base");
        }
    }
}
