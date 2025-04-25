import java.sql.*;

//Klass enbart för att samla valideringsmetoder på ett ställe

public class ValidationUtils {
    public static final String URL = "jdbc:sqlite:webbutiken.db"; //db-koppling här pga valideringsmetoden som ska säkra stockquantity >= quantity använder sql.

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Valideringsmetoder för insertNewCustomer
    public static boolean validationOfEmail(String email) { //Metod för att validera email
        String emailRegex = "^[\\w-\\.]+@[\\w-\\.]+\\.[a-z]{2,}$";
        return email.matches(emailRegex);
    }

    public static boolean validationOfPassword(String password) { //Metod för att validera lösenord
        return password != null && password.length() > 2; //lösenord måste vara mer än 2 tecken
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Valideringsmetoder för getProductByName och getProductByCategory
    public static boolean validationOfProductName(String input) {
        return input != null && !input.isEmpty(); //Sökfält får ej vara tomt
    }

    public static boolean validationOfProductCategory(String input) {
        return input != null && !input.isEmpty(); //Sökfält får ej vara tomt
    }

    /// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Valideringsmetod för updateProductPrice
    public static boolean validationOfProductPrice(double newPrice) {
        return newPrice > 0; //Får ej vara mindre än 0
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Valideringsmetod för insertNewProduct
    public static boolean validationOfNewProduct(String name, double price, double StockQuantity) {
        if (name.length() <= 4) { //produktnamn måste vara längre än 4 tecken
            System.out.println("Invalid product name! The product name must be longer than 4 characters.");
            return false;
        }

        if (price <= 0) { //pris måste vara mer än 0
            System.out.println("Invalid price! The price must more than 0");
            return false;
        }

        if (StockQuantity < 0) { //lagersaldo får ej vara negativt
            System.out.println("Invalid stock quantity! The stock quantity must be 0 or positive");
            return false;
        }
        return true; //Om alla valideringar är ok så returnera true;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Valideringsmetod för att validera quantity mot stock_quantity vid orderläggning
    public static boolean validationOfStockQuantityWhenPlacingOrder(int productId, int orderQuantity) throws SQLException {
        String queryToValidateStockQuantity = "SELECT stock_quantity FROM products WHERE product_id = ?";

        try (Connection conn = DriverManager.getConnection(URL); //Den sjukligt envisa kraschen orsakades troligen av en öppen anslutning. Enligt AI är jag tvungen att lägga till try/catch för att lösa detta. ÄNTLIGEN LÖST!!!!
             PreparedStatement validationOfStockQuantityStmt = conn.prepareStatement(queryToValidateStockQuantity)) {

            validationOfStockQuantityStmt.setInt(1, productId);
            try (ResultSet rs = validationOfStockQuantityStmt.executeQuery()) {

                if (rs.next()) {
                    int stockQuantity = rs.getInt("stock_quantity");
                    return orderQuantity <= stockQuantity;
                } else {
                    System.out.println("The product with " + productId + " could unfortunately not be found");
                }
            }

        } catch (SQLException error) { //Se ovan ang krashen. KOM IHÅG: try/catch kan lösa problem med låst databas pga anslutningarna (Fick sql-error: locked database)
            System.out.println("Oops, something went wrong. Have a coffee and try again later");
            System.err.println("For developer: There was an error when trying to validate stockquantity " + error.getMessage());
        }
        return false;
    }
}
