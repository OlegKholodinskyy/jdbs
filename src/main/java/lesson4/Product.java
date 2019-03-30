package lesson4;

public class Product {
    private long Id;
    private String  name;
    private String description;
    private int price;

    public Product(long id, String name, String description, int price) {
        Id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
