package lesson2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "wersys243900";


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


    public static void main(String[] args) {
        saveProduct();
        deleteProducts();
        deleteProductsByPrice();
    }
}