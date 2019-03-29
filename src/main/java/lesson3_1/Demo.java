package lesson3_1;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Solution solution = new Solution();

        //findProductsByPrice
        List<Product> products = solution.findProductsByPrice(500,100);
        for(Product p : products){
            System.out.println(p);
        }

        //findProductsByName
        List<Product> products2 = null;
        try {
            //      products2 = solution.findProductsByName("doll");
            products2 = solution.findProductsByName("dl122@@@");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (products2 != null) {
            for (Product p : products2) {
                System.out.println(p);
            }
        }

        // findProductsWithEmptyDescription

        List<Product> products3 = null;
        try {
            products3 = solution.findProductsWithEmptyDescription();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (products3 != null) {
            for (Product p : products3) {
                System.out.println(p);
            }
        }



    }
}
