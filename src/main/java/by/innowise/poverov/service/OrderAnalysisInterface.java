package by.innowise.poverov.service;

import by.innowise.poverov.Customer;
import by.innowise.poverov.Order;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OrderAnalysisInterface {

    Set<String> getUniqueCities(List<Order> orders);

    double getTotalIncomeFromDeliveredOrders(List<Order> orders);

    Optional<String> getMostPopularProduct(List<Order> orders);

    List<String> getMostPopularProductList(List<Order> orders);

    double getAverageCheckFromDeliveredOrders(List<Order> orders);

    List<Customer> getCustomersWithMoreThanFiveOrders(List<Order> orders);
}
