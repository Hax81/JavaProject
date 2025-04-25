import java.sql.*;
import java.util.ArrayList;

public class ProductRepository { //Klass som bara ska skicka queries till db och hantera svaren
    public static final String URL = "jdbc:sqlite:webbutiken.db";

//////////////////////////////////////////////////////////////////////////////////////////////
//Visa alla produkter:
    public ArrayList<Product> getAll() throws SQLException { //returtypen är ArrayList
        ArrayList<Product> products = new ArrayList<>(); //Datatypen är Product

        //Upprättar db-koppling. Definierar query och skickar
        //Sparar svaret i resultset
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM products")) { //query genereras

            while (rs.next()) { //Loopar igenom svaret och printar ut. Går igenom alla rader och stannar när rader är slut.
                //En rad=ett objekt
                Product product = new Product( //för varje rad skapas ett nytt objekt //Konstruktorn
                        //Vi tar tag i de kolumner vi vill och sätter dessa värden på våra attribut i produkt-objektet
                        rs.getInt("product_id"),
                        rs.getInt("manufacturer_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("stock_quantity"));

                products.add(product); //sparar objekten i en samling (arraylist) som heter products.
            }
        } catch (SQLException e) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + e.getMessage());
            return new ArrayList<>();  // returnerar tom arraylista om catch-blocket aktiveras
        }
        return products; //returnerar arraylistan products
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
//Söka produkter efter namn
    public ArrayList<Product> getByName(String name) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) { //Måste ha "preparedStatement" om ? ska användas. Se detta: https://www.geeksforgeeks.org/how-to-use-preparedstatement-in-java/
            pstmt.setString(1, "%" + name + "%"); //wildcards för sökresultatet. % både före och efter betyder att vilken del av ordet som helst matchas.

            try (ResultSet rs = pstmt.executeQuery()) { // utför query. Loopa genom resultat.
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("manufacturer_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                    products.add(product); // lägg produkt till arraylistan
                }
            }
        } catch (SQLException e) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + e.getMessage());
            return new ArrayList<>();  // Returning empty list as fallback
        }
        return products;
    }

//////////////////////////////////////////////////////////////////////////////////////////////
//Söka produkter på kategori (SELECT med JOIN på products_categories)
    public ArrayList<Product> getByCategory(String category) throws SQLException {
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT " +
                "products.product_id, " +
                "products.manufacturer_id, " +
                "products.name, " +
                "products.description, " +
                "products.price, " +
                "products.stock_quantity " +
                "FROM products " +
                "JOIN products_categories ON products.product_id = products_categories.product_id " +
                "JOIN categories ON products_categories.category_id = categories.category_id " +
                "WHERE categories.name LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + category + "%"); //wildcards för sökresultatet. % både före och efter betyder att vilken del av ordet som helst matchas.

            try (ResultSet rs = pstmt.executeQuery()) { // utför query. Loopa genom resultat.
                while (rs.next()) {
                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getInt("manufacturer_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDouble("price"),
                            rs.getInt("stock_quantity")
                    );
                    products.add(product); // lägg produkt till arraylistan
                }
            }
        } catch (SQLException error) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + error.getMessage());
            return new ArrayList<>();  //Returnera tom lista om catch-blocket aktiveras.
        }
        return products;
    }
///////////////////////////////////////////////////////////////////////////////////////////
//Uppdatera pris
public boolean updatePrice(int productId, double price) throws SQLException {

    String query = "UPDATE products SET price = ? WHERE product_id = ?";

    try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setDouble(1, price);
        pstmt.setInt(2, productId);

        int rowsUpdated = pstmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException e) {
        System.out.println("Oops, something went wrong. Have a coffee and try again later.");
        System.err.println("For developer only: ERROR: " + e.getMessage());
        return false;
    }
}
////////////////////////////////////////////////////////////////////////////////////////
//Uppdatera lagersaldo
public boolean updateStockQuantity(int productId, double stockQuantity) throws SQLException {

    String query = "UPDATE products SET stock_quantity = ? WHERE product_id = ?";

    try (Connection conn = DriverManager.getConnection(URL);
        PreparedStatement pstmt = conn.prepareStatement(query)) {

        pstmt.setDouble(1, stockQuantity);
        pstmt.setInt(2, productId);

        int rowsUpdated = pstmt.executeUpdate();
        return rowsUpdated > 0;

    } catch (SQLException e) {
        // User-friendly message
        System.out.println("Oops, something went wrong. Have a coffee and try again later.");
        System.err.println("For developer only: ERROR: " + e.getMessage());
        return false;
    }
}
/////////////////////////////////////////////////////////////////////////////////////
//Lägga till nya produkter

public boolean insertNewProduct(int productId, int manufacturerId, String name, String description, double price,
                             double stockQuantity) throws SQLException {

    String query = "INSERT INTO products (product_id, manufacturer_id, name, description, price, stock_quantity) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(URL);
    PreparedStatement pstmt = conn.prepareStatement(query)) {

    pstmt.setInt(1, productId);
    pstmt.setInt(2, manufacturerId);
    pstmt.setString(3, name);
    pstmt.setString(4, description);
    pstmt.setDouble(5, price);
    pstmt.setDouble(6, stockQuantity);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated >0;

        } catch (SQLException e) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + e.getMessage());
            return false;
        }
    }
//////////////////////////////////////////////////////////////////////////////////
}



