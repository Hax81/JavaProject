import java.sql.*;
import java.util.ArrayList;

public class OrdersRepository {
    public static final String URL = "jdbc:sqlite:webbutiken.db"; //db-kopplingen

//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Visa alla ordrar
    public ArrayList<Orders> getAll() throws SQLException { //Metoden. Returtypen är ArrayList
        ArrayList<Orders> orders = new ArrayList<>(); //Datatypen är

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM orders")) { //query genereras

            while (rs.next()) { //Loopar igenom svar och printar ut. Går igenom alla rader och stannar när rader är slut.
                Orders order = new Orders( //för varje rad skapas ett nytt objekt
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date"));

                orders.add(order); //lägger till i orders
            }

        } catch (SQLException e) { //SQL-felhantering
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: Error in database: " + e.getMessage());
        }
        return orders;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////
//Se orderhistorik för en kund
    public ArrayList<Orders> getOrderHistory(int customerId) throws SQLException { //Deklarerar att returtypen är ArrayList som innehåller objekt av klassen Orders
        ArrayList<Orders> orders = new ArrayList<>(); //Skapar en arraylist som heter orders. Ska innehålla objekt som är skapade av Orders-klassen.

        String query = "SELECT customers.name AS CustomerName, " + //Genererar query
                "orders.order_id AS OrderID, " +
                "orders.customer_id AS CustomerID, " +
                "orders.order_date AS Orderdate FROM orders JOIN customers " +
                "ON orders.customer_id = customers.customer_id WHERE customers.customer_id=?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Orders order = new Orders(); //Anropar no arg-konstruktorn i Orders-klassen, skapar objekt.

                    order.setOrder_id(rs.getInt("OrderID")); //Använder setters för att sätta in data
                    order.setCustomer_id(rs.getInt("CustomerID"));
                    order.setOrder_date(rs.getDate("OrderDate")); // OBS! Anledningen till att det heter "OrderDate" är för att jag valt att ha detta som AS i queryn.

                    orders.add(order); //Lägg till objektet order i Arraylistan orders
                }
            } catch (SQLException e) { //SQL-felhantering
                System.out.println("Oops, something went wrong. Have a coffee and try again later.");
                System.err.println("For developer only: Error in database: " + e.getMessage()); //Skriver "For developer..." i konsollen och hämtar o skriver ut felet.
            }
        }
        return orders; //returnera objektet
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////
public Double insertNewOrder(Orders order, ArrayList<Orders_products> orderProducts) {
    String orderQuery = "INSERT INTO orders (customer_id, order_date) VALUES (?, ?)"; //query för att sätta in i orders

    String productQuery = "INSERT INTO orders_products (order_id, product_id, quantity, unit_price) " + //query för att sätta in i orders_products
            "VALUES (LAST_INSERT_ROWID(), ?, ?, ?)";

    String updateStockQuery = "UPDATE products SET stock_quantity = stock_quantity - ? WHERE product_id = ?"; //query för att uppdatera stock_quantity

    String getPriceQuery = "SELECT price FROM products WHERE product_id = ?"; //query för att hämta pris

    try (Connection conn = DriverManager.getConnection(URL);
         PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
         PreparedStatement productStmt = conn.prepareStatement(productQuery);
         PreparedStatement stockStmt = conn.prepareStatement(updateStockQuery);
         PreparedStatement priceStmt = conn.prepareStatement(getPriceQuery)) {

        double totalPrice = 0; // variabel totalPrice som ska lagra ordens totalpris, starta totalpris från 0

        orderStmt.setInt(1, order.getCustomer_id());
        orderStmt.setDate(2, new java.sql.Date(order.getOrder_date().getTime()));
        int rowsUpdated = orderStmt.executeUpdate();

        if (rowsUpdated > 0) { //Om något ändrades så (dvs om en rad sattes in i orders)...
            for (Orders_products orderProduct : orderProducts) { //loopa igenom listan Orderproduct som innehåller objekt av klassen Orders_products
                priceStmt.setInt(1, orderProduct.getProduct_id()); //sätter att produkt-id ska vara parametern i pris-queryn
                ResultSet priceResultSet = priceStmt.executeQuery(); //kör sql-queryn för att priset från tabellen "products" ska hämtas

                if (priceResultSet.next()) { //resultset är objektet som lagrar resultatet av sql-queryn ifråga (här price)
                    double unitPrice = priceResultSet.getDouble("price"); //lagra price (pris på en produkt) i unitPrice

                    totalPrice += unitPrice * orderProduct.getQuantity(); // Lägg ihop priser till totalt pris

                    productStmt.setInt(1, orderProduct.getProduct_id()); //sätt värden i orders_products, en gång för varje produkt som läggs i ordern (loopen)
                    productStmt.setInt(2, orderProduct.getQuantity());
                    productStmt.setDouble(3, unitPrice);
                    productStmt.executeUpdate();

                    stockStmt.setInt(1, orderProduct.getQuantity()); //uppdaterar stock_quantity vid order
                    stockStmt.setInt(2, orderProduct.getProduct_id());
                    stockStmt.executeUpdate();
                } else {
                    System.out.println("The product with ID: " + orderProduct.getProduct_id() + " was not found.");
                    return null; //om produktId ej hittades
                }
            }
            return totalPrice; // returnera totalPris på ordern om den lyckades
        }

    } catch (SQLException error) {
        System.out.println("Oops, something went wrong. Have a coffee and try again later.");
        System.err.println("For developer only: Error in database: " + error.getMessage());
    }
    return null; //om något gick fel
}

////////////////////////////////////////////////////////////////////////////////////////////////////////
}
