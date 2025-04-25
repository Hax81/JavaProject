import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProductController {

    ProductService productService = new ProductService();

    public void runMenu(Scanner scanner) throws SQLException {
        runMenuContainMents(scanner);
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Menymetod
    public void runMenuContainMents(Scanner scanner) throws SQLException {
        System.out.println("---Product menu---");
        System.out.println("Please select: ");
        System.out.println("----------------------");
        System.out.println("1. List all products"); //Cases
        System.out.println("2. Search for a product by name");
        System.out.println("3. Search for a product by category");
        System.out.println("4. Update price of product");
        System.out.println("5. Update stockquantity of product");
        System.out.println("6. Add new product");
        System.out.println("7. Return to main menu");
        System.out.println("----------------------");
        String select = scanner.nextLine();


        switch (select) {
            case "1":
                displayAllProducts();
                break;

            case "2":
                getProductByName(scanner);
                break;

            case "3":
                getProductByCategory(scanner);
                break;

            case "4":
                updateProductPrice(scanner);
                break;

            case "5":
                updateProductStockQuantity(scanner);
                break;

            case "6":
                insertANewProduct(scanner);
                break;

            case "7":
                System.out.println("Returning to main menu...\n");
                return;

            default:
                System.out.println("Invalid input! Please select 1, 2, 3, 4, 5 or 6");
        }
    }

    //*********************************************************************************************************************
    public void displayAllProducts() throws SQLException {
        System.out.println("---All products---\n");
        System.out.println("-----------------------------------------------");
        ArrayList<Product> products = productService.getAllProducts();
        for (Product product : products) {
            System.out.println("ProduktId: " + product.getProductId());
            System.out.println("ManufacturerId: " + product.getManufacturerId()); //osv för alla attributen...
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Stockquantity: " + product.getStockQuantity());
            System.out.println("--------------------------------------------");
        }
    }

    //*********************************************************************************************************************
    public void getProductByName(Scanner scanner) throws SQLException {

        System.out.print("Input name of product: ");
        String name = scanner.nextLine();

        if (!ValidationUtils.validationOfProductName(name)) { //Validera
            System.out.println("Search field must not be empty. Please input a product name.");
            return;
        }
            ArrayList<Product> searchedProductsByName = productService.getByName(name);
            for (Product product : searchedProductsByName) {
                System.out.println("ProduktId: " + product.getProductId());
                System.out.println("ManufacturerId: " + product.getManufacturerId()); //osv för alla attributen...
                System.out.println("Name: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Stockquantity: " + product.getStockQuantity());

            }
        }
//*********************************************************************************************************************
        public void getProductByCategory (Scanner scanner) throws SQLException {

            System.out.print("Input category of product: ");
            String category = scanner.nextLine();

            if (!ValidationUtils.validationOfProductCategory(category)) { //Validera
                System.out.println("Search field must not be empty. Please input a product category.");
                return;
            }

            ArrayList<Product> searchedProductsByCategory = productService.getByCategory(category);
            for (Product product : searchedProductsByCategory) {
                System.out.println("ProduktId: " + product.getProductId());
                System.out.println("ManufacturerId: " + product.getManufacturerId()); //osv för alla attributen...
                System.out.println("Name: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Price: " + product.getPrice());
                System.out.println("Stockquantity: " + product.getStockQuantity());

            }
        }
//*********************************************************************************************************************
        public void updateProductPrice (Scanner scanner) throws SQLException {

            System.out.println("Input productId and new price of the product you want to update");

            System.out.println("Enter productId: ");
            int productIdNewPrice = scanner.nextInt();

            System.out.println("Enter new price: ");
            double newPrice = scanner.nextDouble();

            if (!ValidationUtils.validationOfProductPrice(newPrice)) { //Validera
                System.out.println("Price must be bigger than 0. Please enter a valid value");
                return;
            }

            boolean priceIsUpdated = productService.updatePrice(productIdNewPrice, newPrice); //anropar updatePrice() i productService. Sparar resultat (value true eller false pga updatePrice returnerar en bool) i en boolean variabel som heter priceIsUpdated.

            if (priceIsUpdated) {
                System.out.println("Product price was successfully updated!");
            } else {
                System.out.println("The attempt to update price failed. Please be so kind as to check that productId has been correctly put in");
            }
        }
//*********************************************************************************************************************
        public void updateProductStockQuantity (Scanner scanner) throws SQLException {

            System.out.println("Input productId and new stockquantity of the product you want to update");

            System.out.println("Enter productId: ");
            int productIdNewStockQuantity = scanner.nextInt();

            System.out.println("Enter new stockquantity: ");
            double newStockQuantity = scanner.nextDouble();

            boolean stockQuantityIsUpdated = productService.updateStockQuantity(productIdNewStockQuantity, newStockQuantity);

            if (stockQuantityIsUpdated) {
                System.out.println("Stockquantity was successfully updated!");
            } else {
                System.out.println("The attempt to update stockquantity failed. Please be so kind as to check that productId has been correctly put in");
            }
        }
//*********************************************************************************************************************
        public void insertANewProduct (Scanner scanner) throws SQLException {

            System.out.println("Please enter productId, manufacturerid, name, description, price and " +
                    "stock_quantity of the product you wish to add");

            System.out.println("Enter productId: "); //Id för produkten som ska läggas till
            int IdOfNewProduct = scanner.nextInt();

            System.out.println("Enter manufacturerid: "); //Tillverkarens Id
            int IdOfNewManufacturer = scanner.nextInt();
            scanner.nextLine(); //Enligt AI måste denna vara med för att nästa input inte ska hoppas över. Rensar buffert.

            System.out.println("Enter name of product: "); //Produktnamn
            String nameOfNewProduct = scanner.nextLine();

            System.out.println("Enter description of product: "); //Beskrivning
            String descriptionOfNewProduct = scanner.nextLine();

            System.out.println("Enter price of product: "); //Pris
            double priceOfNewProduct = scanner.nextDouble();

            System.out.println("Enter stockquantity of product: "); //Lagersaldo
            double stockQuantityOfNewProduct = scanner.nextDouble();


            if (!ValidationUtils.validationOfNewProduct(nameOfNewProduct, priceOfNewProduct, stockQuantityOfNewProduct)) { //Validera input av namn, pris och lagersaldo för ny produkt
                return; //Om validering misslyckas avsluta
            }

                boolean productIsAdded = productService.insertNewProduct(IdOfNewProduct, IdOfNewManufacturer,
                    nameOfNewProduct, descriptionOfNewProduct, priceOfNewProduct, stockQuantityOfNewProduct);

            if (productIsAdded) {
                System.out.println("Product was successfully added!");
            } else {
                System.out.println("Error! The attempt to add product failed. Please be so kind as to check that " +
                        "the product information was correctly put in");
            }
        }
//*********************************************************************************************************************
}

