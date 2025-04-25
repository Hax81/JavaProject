public class Orders_products {
    private int order_product_id;
    private int order_id;
    private int product_id;
    private int quantity;
    private double unit_price;

    public Orders_products(int order_product_id, int order_id, int product_id, int quantity, double unit_price) { //Genererad konstruktor
        this.order_product_id = order_product_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public Orders_products(int product_id, int quantity, double unit_price) { //Ytterligare konstruktor utan order_product_id och order_id eftersom det är endast dessa som ska anges av användaren i uppgift 3 (Overloading av konstruktorer)
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }


    public int getOrder_product_id() {
        return order_product_id;
    }

    public void setOrder_product_id(int order_product_id) {
        this.order_product_id = order_product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
