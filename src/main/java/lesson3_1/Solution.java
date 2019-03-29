package lesson3_1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public List<Product> findProductsByPrice(int price, int delta) {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {


            String query = "SELECT * FROM PRODUCT WHERE PRICE <= " + (price + delta) + " AND PRICE >= " + (price - delta);
            ResultSet res = statement.executeQuery(query);

            while (res.next()) {

                products.add(new Product(res.getLong(1),
                        res.getString(2),
                        res.getString(3),
                        res.getInt(4)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return products;

    }

    public List<Product> findProductsByName(String word) throws Exception {


        ckeckWord(word);

        ArrayList<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {


            String query = "SELECT * FROM PRODUCT WHERE NAME LIKE  '%" + word + "%'";
            ResultSet res = statement.executeQuery(query);

            while (res.next()) {

                products.add(new Product(res.getLong(1),
                        res.getString(2),
                        res.getString(3),
                        res.getInt(4)));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return products;


    }

    private void ckeckWord(String word) throws Exception {

        String[] words = word.trim().split(" ");

        if (word == null) throw new Exception("word is null");
        if (words.length != 1) throw new Exception("sentence must contain only one word");
        if (word.length() <= 3) throw new Exception("word must contain more than 3 sumbols");

        char[] arr = word.toCharArray();
        for (char c : arr) {
            if (!Character.isLetter(c) && !Character.isDigit(c))
                throw new Exception("word must contain only digital or letter");
        }
    }

    public ArrayList<Product> findProductsWithEmptyDescription() {
        ArrayList<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE DESCRIPTION IS NULL");

            while (resultSet.next()){
                Product product = new Product(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
                products.add(product);
            }

        } catch (SQLException e) {

        }
        return products;
    }

}
