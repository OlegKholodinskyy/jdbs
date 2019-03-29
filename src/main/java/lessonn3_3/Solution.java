package lessonn3_3;

import java.sql.*;
import java.util.Random;

public class Solution {

    private static final String DB_URL = "jdbc:oracle:thin:@gromcode-lessons.cmbqecodcoqo.us-east-2.rds.amazonaws.com:1521:ORCL";
    private static final String USER = "main";
    private static final String PASS = "xxx";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public long testSpeedPerformance() throws SQLException {
        long timeBefore = System.currentTimeMillis();
        long i = 1000;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TEST_SPEED VALUES (?,?,?)")) {


            for (int z = 1; z <= 1000; z++) {
                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, generateRandomString());
                preparedStatement.setLong(3, generateRandomDigit());

                preparedStatement.executeUpdate();
                i++;
            }
        }

        long timeAfter = System.currentTimeMillis();
        return (timeAfter - timeBefore);
    }

    private long generateRandomDigit() {
        return (int) (Math.random() * 100);
    }

    private String generateRandomString() {
        Random random = new Random();
        String allLetters = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder stringBuilder = new StringBuilder("Some String : ");
        for (int z = 0; z <= 5; z++) {
            int i = (int) (Math.random() * 26);
            stringBuilder.append(allLetters.charAt(i));
        }
        return stringBuilder.toString();
    }


    public long testDeleteByIdPerformance() throws SQLException {
        long timeBefore = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM TEST_SPEED WHERE ID = ?")) {
            for (int i = 1000; i <= 1999; i++) {
                preparedStatement.setLong(1, i);
                preparedStatement.executeUpdate();
            }

            long timeAfter = System.currentTimeMillis();
            return (timeAfter - timeBefore);
        }
    }

    public long testDeletePerformance() throws SQLException {
        long timeBefore = System.currentTimeMillis();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
             statement.executeUpdate("DELETE FROM TEST_SPEED WHERE ID >= 1000 AND ID <=1999");

            long timeAfter = System.currentTimeMillis();
            return (timeAfter - timeBefore);
        }
    }



    public long testSelectByIdPerformance() throws SQLException {
        long timeBefore = System.currentTimeMillis();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM TEST_SPEED WHERE ID = ?")) {
            for (int i = 1000; i <= 1999; i++) {
                preparedStatement.setLong(1, i);
                preparedStatement.executeQuery();
            }

            long timeAfter = System.currentTimeMillis();
            return (timeAfter - timeBefore);
        }
    }


    public long testSelectPerformance() throws SQLException {
        long timeBefore = System.currentTimeMillis();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeQuery("SELECT * FROM TEST_SPEED WHERE ID >= 1000 AND ID <=1999");

            long timeAfter = System.currentTimeMillis();
            return (timeAfter - timeBefore);
        }
    }
}