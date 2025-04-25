
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersService {

    OrdersRepository ordersRepository = new OrdersRepository();


    public ArrayList<Orders> getAllOrders () throws SQLException {
        return ordersRepository.getAll(); //returnerar en arraylist av orders
    }

    public ArrayList<Orders> getOrderHistory (int customerId) throws SQLException {
        return ordersRepository.getOrderHistory(customerId);
    }

    public Double insertNewOrder(Orders order, ArrayList<Orders_products> orderProducts) throws SQLException {
        return ordersRepository.insertNewOrder(order, orderProducts);

        }

}
