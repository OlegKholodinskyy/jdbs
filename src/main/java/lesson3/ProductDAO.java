package lesson3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";


    public Product save(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO PRODUCT VALUES (?,?,?,?)")) {

            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getPrice());

            int res = ps.executeUpdate();

            System.out.println("Save was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something wrong");
            e.printStackTrace();
        }
        return product;
    }


    public Product update(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE PRODUCT SET NAME = ? , DESCRIPTION = ?, PRICE = ? WHERE ID = ?")) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setInt(3, product.getPrice());
            ps.setLong(4, product.getId());

            int res = ps.executeUpdate();

            System.out.println("UDATE was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something wrong");
            e.printStackTrace();
        }
        return product;
    }


    public List<Product> getProducts() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.out.println("SOMETHING WENT WRONG");
            e.printStackTrace();
        }
        return null;
    }


    public void delete(long id) {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM PRODUCT WHERE ID = ?")) {

            ps.setLong(1,id);

            int result = ps.executeUpdate();
            if (result != 0)
                System.out.println("Product with id : " + id + " deleted.");
            else{
                System.out.println("NOTHING DELETED");
            }

        } catch (SQLException e) {
            System.out.println("SOMETHING WENT WRONG");
            e.printStackTrace();
        }
    }


    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
