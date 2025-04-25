public class Product { //Skapar här Product-klassen enligt ERD
    private int productId;
    private int manufacturerId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;

    public Product(int productId, int manufacturerId, String name, String description, double price, //konstruktor
                   int stockQuantity) {
        this.productId = productId;
        this.manufacturerId = manufacturerId;
        this.name=name;
        this.description=description;
        this.price=price;
        this.stockQuantity=stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}
