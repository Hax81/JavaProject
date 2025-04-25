import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
                Scanner scanner = new Scanner(System.in); //ETT scannerobjekt skapas och ges till alla controllers senare. Löste problem med kraschen av att hela tiden öppna o stänga scannern i controllers.
                boolean programrunning = true;

                while (programrunning) {
                    System.out.println("Main Menu");
                    System.out.println("----------");
                    System.out.print("Please select: \n");
                    System.out.println("1. Customer Submenu");
                    System.out.println("2. Product Submenu");
                    System.out.println("3. Orders Submenu");
                    System.out.println("4. Exit");
                    System.out.println("-----------");


                    int inputToChoose = scanner.nextInt();
                    scanner.nextLine(); //rensa buffert...

                    switch (inputToChoose) {
                        case 1:
                            CustomerController customerController = new CustomerController(); //Objekt skapas av klassen och sparas i variabel
                            customerController.runMenu(scanner);
                            break;

                        case 2:
                            ProductController productController = new ProductController();
                            productController.runMenu(scanner);
                            break;

                        case 3:
                            OrdersController ordersController = new OrdersController();
                            ordersController.runMenu(scanner);
                            break;

                        case 4:
                            programrunning = false;
                            System.out.println("Goodbye!");
                            break;

                        default:
                            System.out.println("Invalid input! Please select 1, 2, 3 or 4");
                    }
                }
                scanner.close(); //Stänger scanner endast här. Löste ÄNTLIGEN problemet med att stängning i controllers orsakar krasch
            }
}


