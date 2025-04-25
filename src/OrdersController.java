import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class OrdersController {

    OrdersService ordersService = new OrdersService();

    public void runMenu(Scanner scanner) throws SQLException {
        runMenuContainments(scanner);
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Menymetod
    public void runMenuContainments(Scanner scanner) throws SQLException {
        System.out.println("---Orders menu---");
        System.out.println("Please select: ");
        System.out.println("----------------------");
        System.out.println("1. Display all orders");//Cases
        System.out.println("2. Display order history of a customer");
        System.out.println("3. Insert a new order");
        System.out.println("4. Return to main menu");
        System.out.println("-----------------------");

        String select = scanner.nextLine();
        switch (select) {
            case "1":
                displayAllOrders();
                break;

            case "2":
                orderHistoryOfCustomer(scanner);
                break;


            case "3":
                insertANewOrder(scanner);
                break;

            case "4":
                System.out.println("Returning to main menu...\n");
                return;

            default:
                System.out.println("Invalid input! Please select 1, 2, 3 or 4");
        }
    }

//*********************************************************************************************************************
//Övriga metoder
    public void displayAllOrders() throws SQLException {
        System.out.println("---All orders---\n");
        System.out.println("---------------------------------");
        ArrayList<Orders> orders = ordersService.getAllOrders();

        for (Orders order : orders) {
            System.out.println("Order Id: " + order.getOrder_id());
            System.out.println("Customer Id: " + order.getCustomer_id());
            System.out.println("Order date: " + order.getOrder_date());
            System.out.println("-------------------------------------");
        }
    }
//*********************************************************************************************************************

    public void orderHistoryOfCustomer(Scanner scanner) throws SQLException {

        System.out.println("Please enter: \n");

        System.out.println("Id of customer: ");
        int idOfCustomer = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Orders> ordersHistory = ordersService.getOrderHistory(idOfCustomer);

        if (ordersHistory.isEmpty()) {
            System.out.println("No orders found for the customer with id " + idOfCustomer);
        } else {
            System.out.println("Order history for customer with id " + idOfCustomer + " :");
            for (Orders order : ordersHistory) {
                System.out.println("Order Id: " + order.getOrder_id());
                System.out.println("Order date: " + order.getOrder_date());
                System.out.println("                                     ");
            }
        }
    }

//*********************************************************************************************************************
public void insertANewOrder(Scanner scanner) {
    try {
        System.out.println("Enter customer ID:");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter order date (YYYY-MM-DD):");
        String dateInput = scanner.nextLine();
        Date orderDate = Date.valueOf(LocalDate.parse(dateInput));

        Orders order = new Orders(0, customerId, orderDate); // Skapa en instans (dvs objekt) av klassen Orders med hjälp av en av Orders-klassens konstruktorer
                                                                    //0 sätts för order_id pga detta autoinkrementas. Det är alltså inte tilldelat än när objektet skapas vad jag förstår.
        ArrayList<Orders_products> orderProducts = new ArrayList<>();
        boolean addMoreProducts = true;

        while (addMoreProducts) {
            try {
                System.out.println("Enter product ID:");
                int productId = scanner.nextInt();
                scanner.nextLine(); // Rensa buffert efter int

                System.out.println("Enter quantity:");
                int quantity = scanner.nextInt();
                scanner.nextLine(); // Rensa buffert efter int

                //VIKTIGA VALIDERINGEN (SE NILS INSTRUKTIONER): Validera stock_quantity mot quantity i ordern. Anropar metoden i ValidationUtils.
                if (ValidationUtils.validationOfStockQuantityWhenPlacingOrder(productId, quantity)) {

                    orderProducts.add(new Orders_products(productId, quantity, 0)); // Förändring: Tidigare frågades efter pris här men nu hämtas priset från databasen. 0 sätts "från början" dvs innan priset hämtats.

                    System.out.println("Product added successfully!");
                } else { //Om valideringen inte godkänns pga stockquantity < quantity så printa...
                    System.out.println("The stockquantity for the product with productId: " + productId + " is not enough for the quantity: " + quantity + " in this order. Product was therefore not added.");
                    continue; //Gå vidare utan att lägga till produkten. Borde egentligen be om annan kvantitet? Hur göra detta?
                }

                System.out.println("Do you wish to add another product? (yes/no):"); //Vägskäl: Vill du fortsätta lägga till produkter?
                String response = scanner.nextLine().trim().toLowerCase(); //trim() och toLowerCase() körs på inputen. Kom ihåg det här tipset.

                if (response.equals("yes")) {
                    addMoreProducts = true; //Om ja, fortsätt loop
                } else if (response.equals("no")) {
                    addMoreProducts = false; //Om nej, gå ur loop och fortsätt till nästa steg
                } else {
                    System.out.println("Invalid input! Input must be yes or no.");
                }
            } catch (Exception e) {
                System.out.println("Invalid input! Please try again.");
                scanner.nextLine(); // Rensa buffert igen tydligen...
            }
        }

        Double totalPrice = ordersService.insertNewOrder(order, orderProducts);

        if (totalPrice != null) { //om totalpriset inte är null så...
            System.out.println("Order created successfully! The total price is: " + totalPrice);
        } else {
            System.out.println("Order could not be created. Please try again!");
        }
    } catch (Exception error) {
        System.out.println("Oops, something went wrong. Have a coffee and try again later.");
        System.err.println("For developer: An error occurred: " + error.getMessage());
    }
}

//*********************************************************************************************************************
}








