import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {
    public static final String URL = "jdbc:sqlite:webbutiken.db";

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Visa alla kunder
    public ArrayList<Customer> getAll() throws SQLException { //returtypen är ArrayList

        ArrayList<Customer> customers = new ArrayList<>(); //Datatypen är Customer

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) { //query genereras

            while (rs.next()) { //Loopar igenom svar och printar ut. Går igenom alla rader och stannar när rader är slut.
                Customer customer = new Customer( //för varje rad skapas ett nytt objekt
                        rs.getInt(1),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("Password"));

                customers.add(customer); //lägger till i customers
            }
        } catch (SQLException e) { //SQL-felhantering
            System.out.println("Oops, something went wrong. Have a coffee and try again later."); //Trevligt felmeddelande till användaren
            System.err.println("For developer only: ERROR: " + e.getMessage()); //Skriver ut "For developer..." och skriver ut felmeddelandet
        }
        return customers;
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
//Lägg till en kund
    public boolean insertNewCustomer(int customerId, String name, String email, String phone, String adress,
                                     String password) throws SQLException {

        String query = "INSERT INTO customers (customer_id, name, email, phone, address, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, customerId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, phone);
            pstmt.setString(5, adress);
            pstmt.setString(6, password);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + e.getMessage());
            return false;
        }
    }


///////////////////////////////////////////////////////////////////////////////////////////////////////////
//Uppdatera email hos kund
    public boolean updateCustomerEmail(int customer_Id, String newEmail) throws SQLException {
        String query = "UPDATE customers SET email = ? WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newEmail);
            pstmt.setInt(2, customer_Id);

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Oops, something went wrong. Have a coffee and try again later.");
            System.err.println("For developer only: ERROR: " + e.getMessage());
            return false;
        }
    }
}


