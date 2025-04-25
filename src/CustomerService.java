
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

    public ArrayList<Customer> getAllCustomers () throws SQLException {
        return customerRepository.getAll(); //returnerar en arraylist av customers
}

    public boolean insertNewCustomer (int customerId, String name, String email, String phone, String adress, String password) throws SQLException {
        return customerRepository.insertNewCustomer(customerId, name, email, phone, adress, password);
}

    public boolean updateCustomerEmail (int customer_id, String email) throws SQLException {
        return customerRepository.updateCustomerEmail (customer_id, email);
    }

}
