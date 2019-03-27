package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        //SAVE
        Product product = new Product(10, "test", "testDesctiption", 99);
        productDAO.save(product);
        //UPDATE
        Product product2 = new Product(996, "testUpdate", "testUpdateDesctiption", 999);
        productDAO.update(product2);
        // DELETE
        productDAO.delete(10);
        //GET_ALL_PRODUCTS
        System.out.println(productDAO.getProducts());

    }
}
