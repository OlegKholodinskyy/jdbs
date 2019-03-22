package lesson2;

import java.sql.*;
import java.util.ArrayList;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "243900243900";
    static ArrayList<Product> listOfProducts;

    public static void saveProduct() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            int resSave = statement.executeUpdate("INSERT INTO PRODUCT VALUES (999, 'toy', 'for children', 60)");
            System.out.println("Insert into DB. Result : " + resSave);

            int resSave2 = statement.executeUpdate("INSERT INTO PRODUCT VALUES (998, 'toy', 'for children', 120)");
            System.out.println("Insert into DB. Result : " + resSave2);

            int resSave3 = statement.executeUpdate("INSERT INTO PRODUCT VALUES (997, 'toy3', 'for children', 160)");
            System.out.println("Insert into DB. Result : " + resSave3);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProducts() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            int resSave = statement.executeUpdate("DELETE FROM PRODUCT WHERE NAME != 'toy'");
            System.out.println("Delete from DB. Result : " + resSave);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteProductsByPrice() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            int resSave = statement.executeUpdate("DELETE FROM PRODUCT WHERE PRICE < 100 ");
            System.out.println("Delete from DB. Result : " + resSave);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Product> getAllProducts() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
            listOfProducts = new ArrayList<>();
            while (resultSet.next()) {
                listOfProducts.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)));
            }

            for (Product product : listOfProducts)
                System.out.println(product.toString());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfProducts;
    }

    public static ArrayList<Product> getProductsByPrice() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE PRICE <=100 ORDER BY PRICE");
            listOfProducts = new ArrayList<>();
            while (resultSet.next()) {
                listOfProducts.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)));
            }

            for (Product product : listOfProducts)
                System.out.println(product.toString());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfProducts;
    }

    public static ArrayList<Product> getProductsByDescription() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE DBMS_LOB.GETLENGTH(DESCRIPTION)>50");
            listOfProducts = new ArrayList<>();
            while (resultSet.next()) {
                listOfProducts.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)));
            }

            for (Product product : listOfProducts)
                System.out.println(product.toString());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return listOfProducts;
    }


    public static void increasePrice() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            int countOfProductsChanged = statement.executeUpdate("UPDATE PRODUCT SET PRICE = PRICE + 100 WHERE PRICE < 970");
            System.out.println("Updated + " + countOfProductsChanged + "fields");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void changeDescription() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE DBMS_LOB.GETLENGTH(DESCRIPTION)>100");
            listOfProducts = new ArrayList<>();
            while (resultSet.next()) {
                listOfProducts.add(new Product(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4)));
            }


            for (Product product : listOfProducts){
                String query = "UPDATE PRODUCT SET DESCRIPTION = '" + cutLastSentence(product.getDescription()) + "' WHERE ID = " + product.getId();
                int res = statement.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String cutLastSentence(String someText) {
        int lastIndex = 0;
        int prev = 0;
        for (int i = 0; i < someText.length(); i++) {
            if (someText.charAt(i) == '.' || someText.charAt(i) == '?' || someText.charAt(i) == '!') {
                prev = lastIndex;
                lastIndex = i;
            }
        }
            String cutLastSentenceString = someText.substring(0, prev+1);
            return cutLastSentenceString;
    }


    public static void main(String[] args) {
        // saveProduct();
        // deleteProducts();
        // deleteProductsByPrice();
        // getAllProducts();
        // getProductsByPrice();
        // getProductsByDescription();
        //increasePrice();
        changeDescription();
    }
}