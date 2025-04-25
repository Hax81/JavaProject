import java.sql.*;
import java.util.ArrayList;

public class CustomerRepository {

    public static final String URL = "jdbc:sqlite:webbutiken.db";

    public void getAll() throws SQLException {

        System.out.println("Detta är metoden för att hämta alla customers getAll()");
        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {

            while (rs.next()) {
                System.out.println(rs.getInt("customer_id"));
                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("email"));
                System.out.println(rs.getString("phone"));
                System.out.println(rs.getString("address"));
                System.out.println(rs.getString("password"));
            }
        }
    }
}
