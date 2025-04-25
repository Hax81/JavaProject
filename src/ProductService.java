import java.sql.SQLException;
import java.util.ArrayList;
//Detta är vårt logiska lager. Här hanteras uträkningar och annat fett


public class ProductService {

    ProductRepository productRepository = new ProductRepository();

    public ArrayList<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }

    public ArrayList<Product> getByName (String name) throws SQLException {
        return productRepository.getByName(name);
    }

    public ArrayList<Product> getByCategory (String category) throws SQLException {
        return productRepository.getByCategory(category);
    }

    public boolean updatePrice (int productId, Double price) throws SQLException {
        return productRepository.updatePrice(productId, price);
    }

    public boolean updateStockQuantity (int productId, double stockQuantity) throws SQLException {
        return productRepository.updateStockQuantity(productId, stockQuantity);
    }

    public boolean insertNewProduct (int productId, int manufacturerId, String name, String description,
                                  double price, double stockQuantity) throws SQLException {
        return productRepository.insertNewProduct(productId, manufacturerId, name, description, price, stockQuantity);

    }


}
