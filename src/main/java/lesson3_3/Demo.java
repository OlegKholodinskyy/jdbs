package lesson3_3;

import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) {
        Solution solution = new Solution();


        //  Adding 1000 records into table TEST_SPEED takes : 162374 milliseconds.
        try {
           long elapsedTime =  solution.testSpeedPerformance();
            System.out.println("Adding 1000 records into table TEST_SPEED takes : " + elapsedTime + " milliseconds.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Delete 1000 records from table TEST_SPEED by ID takes : 161209 milliseconds.
        try {
            long elapsedTime =  solution.testDeleteByIdPerformance();
            System.out.println("Delete 1000 records from table TEST_SPEED by ID takes : " + elapsedTime + " milliseconds.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete 1000 records from table TEST_SPEED takes : 5337 milliseconds.
        try {
            long elapsedTime =  solution.testDeletePerformance();
            System.out.println("Delete 1000 records from table TEST_SPEED takes : " + elapsedTime + " milliseconds.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Select 1000 records from table TEST_SPEED by ID  takes : 162932 milliseconds.
        try {
            long elapsedTime =  solution.testSelectByIdPerformance();
            System.out.println("Select 1000 records from table TEST_SPEED by ID  takes : " + elapsedTime + " milliseconds.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Select 1000 records from table TEST_SPEED  takes : 1925 milliseconds.
        try {
            long elapsedTime =  solution.testSelectPerformance();
            System.out.println("Select 1000 records from table TEST_SPEED  takes : " + elapsedTime + " milliseconds.");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
