import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;


public class CustomerController {

    CustomerService customerService = new CustomerService();

        public void runMenu (Scanner scanner) throws SQLException {
            runMenuContainMents(scanner);
        }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        public void runMenuContainMents (Scanner scanner) throws SQLException {
            System.out.println("---Customer menu---");
            System.out.println("Please select: ");
            System.out.println("----------------------");
            System.out.println("1. Display all customers"); //Cases
            System.out.println("2. Add a new customer");
            System.out.println("3. Update the email of a customer");
            System.out.println("4. Return to main menu");
            System.out.println("----------------------");
            String select = scanner.nextLine();
            switch (select) {
                case "1":
                    displayALlCustomers();
                    break;

                case "2":
                    addACustomer(scanner);
                    break;

                case "3":
                    updateCustomerEmail(scanner);
                    break;

                case "4":
                    System.out.println("Returning to main menu...\n");
                    return;

                default:
                    System.out.println("Invalid input! Please select 1, 2, or 3");
        }
    }

//*********************************************************************************************************************
        public void displayALlCustomers () throws SQLException {
            System.out.println("---All customers---\n");
            System.out.println("---------------------------------------");
            ArrayList<Customer> customers = customerService.getAllCustomers();
            for (Customer customer:customers) {
                System.out.println("KundId: "+ customer.getCustomerId());
                System.out.println("Namn: " + customer.getName()); //osv för alla attributen...
                System.out.println("Email: " + customer.getEmail());
                System.out.println("Phone: " + customer.getPhone());
                System.out.println("Adress: " + customer.getAdress());
                System.out.println("Password: " + customer.getPassword());
                System.out.println("-----------------------------------");
            }
        }
//*********************************************************************************************************************
        public void addACustomer (Scanner scanner) throws SQLException {

            System.out.println("Please enter: \n");

            System.out.println("Id of new customer: ");
            int idOfNewCustomer = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Name of new customer: ");
            String nameOfNewCustomer = scanner.nextLine();

            System.out.println("Email of new customer: ");
            String emailOfNewCustomer = scanner.nextLine();
            while (!ValidationUtils.validationOfEmail(emailOfNewCustomer)) { //Validera email
                System.out.println("Invalid input. The format is incorrect. Enter valid format: ");
                emailOfNewCustomer = scanner.nextLine();
            }

            System.out.println("Phone number of new customer: ");
            String phoneOfNewCustomer = scanner.nextLine();

            System.out.println("Adress of new customer: ");
            String adressOfNewCustomer = scanner.nextLine();


            System.out.println("Password of new customer: ");
            String passwordOfNewCustomer = scanner.nextLine();
            while (!ValidationUtils.validationOfPassword(passwordOfNewCustomer)) { //Validera lösenord
                System.out.println("Password length too short. Password must be longer than 2 characters");
                passwordOfNewCustomer = scanner.nextLine();
            }

            boolean customerIsAdded = customerService.insertNewCustomer(idOfNewCustomer,
                    nameOfNewCustomer, emailOfNewCustomer, phoneOfNewCustomer, adressOfNewCustomer,
                    passwordOfNewCustomer);

            if(customerIsAdded) {
                System.out.println("Customer was successfully added!");
            } else {
                System.out.println("Error! Customer could NOT be added. Please check that the customer " +
                        "information is correct");
            }
        }
//*********************************************************************************************************************
        public void updateCustomerEmail (Scanner scanner) throws SQLException {

            System.out.println("Please enter: \n");

            System.out.println("Id of the customer: ");
            int idOfCustomerMailUpdate = scanner.nextInt();
            scanner.nextLine();


            System.out.println("New mail: ");
            String updatedEmailOfCustomer = scanner.nextLine();
            while (!ValidationUtils.validationOfEmail(updatedEmailOfCustomer)) { //Validera email
                System.out.println("Invalid input. The format is incorrect. Enter valid format: ");
                updatedEmailOfCustomer = scanner.nextLine();
            }

            boolean mailIsUpdated = customerService.updateCustomerEmail(idOfCustomerMailUpdate, updatedEmailOfCustomer);

            if(mailIsUpdated) {
                System.out.println("Mail was successfully updated!");
            } else {
                System.out.println("Error! Mail could NOT be updated. Please check that the customerId " +
                        "is correct");
            }
        }
//*********************************************************************************************************************
}
