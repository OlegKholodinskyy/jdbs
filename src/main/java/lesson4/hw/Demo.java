package lesson4.hw;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        Product product1 = new Product(501, "oneProduct", "description", 55);
        Product product2 = new Product(402, "secondProduct", "description", 155);
        Product product3 = new Product(403, "thirdProduct", "description", 255);

        List<Product> products = new ArrayList<>();

        products.add(product1);
        products.add(product2);
        products.add(product3);

        TransactionDemo.save(products);
    }
}
