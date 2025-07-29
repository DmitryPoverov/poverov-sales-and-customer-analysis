package by.innowise.poverov.service;

import by.innowise.poverov.Customer;
import by.innowise.poverov.Order;
import by.innowise.poverov.OrderItem;
import by.innowise.poverov.OrderStatus;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderAnalysisService implements OrderAnalysisInterface {

    @Override
    public Set<String> getUniqueCities(List<Order> orders) {
        return orders
                .stream()
                .map(Order::getCustomer)
                .filter(Objects::nonNull)
                .map(Customer::getCity)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }


    @Override
    public double getTotalIncomeFromDeliveredOrders(List<Order> orders) {
        return orders
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(orderItem -> orderItem.getQuantity() * orderItem.getPrice())
                .sum();
    }


    @Override
    public Optional<String> getMostPopularProduct(List<Order> orders) {
        return orders
                .stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }


    @Override
    public List<String> getMostPopularProductList(List<Order> orders) {
        Map<String, Integer> allProductsAndQuantities = orders
                .stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName, Collectors.summingInt(OrderItem::getQuantity)));

        if (allProductsAndQuantities.isEmpty()) {
            return Collections.emptyList();
        }

        Integer maxQuantity = Collections.max(allProductsAndQuantities.values());

        return allProductsAndQuantities
                .entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), maxQuantity))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    @Override
    public double getAverageCheckFromDeliveredOrders(List<Order> orders) {
        return orders
                .stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order
                                                .getItems()
                                                .stream()
                                                .mapToDouble(orderItem -> orderItem.getQuantity() * orderItem.getPrice())
                                                .sum())
                .average()
                .orElse(0.0);
    }


    @Override
    public List<Customer> getCustomersWithMoreThanFiveOrders(List<Order> orders) {
        return orders
                .stream()
                .map(Order::getCustomer)
                .collect(Collectors.groupingBy(customer -> customer, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
