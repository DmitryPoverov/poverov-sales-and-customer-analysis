package by.innowise.poverov.service;

import by.innowise.poverov.Customer;
import by.innowise.poverov.Order;
import by.innowise.poverov.OrderItem;
import by.innowise.poverov.OrderStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

class OrderAnalysisServiceTest {

    private static List<Order> orders;
    private static OrderAnalysisInterface service;

    private static Customer customer(String id, String city) {
        return Customer.builder()
                .customerId(id)
                .city(city)
                .build();
    }

    private static OrderItem item(String name, int quantity, double price) {
        return OrderItem.builder()
                .productName(name)
                .quantity(quantity)
                .price(price)
                .build();
    }

    private static Order order(String id, OrderStatus status, Customer customer, OrderItem... items) {
        return Order.builder()
                .orderId(id)
                .status(status)
                .customer(customer)
                .items(Arrays.asList(items))
                .build();
    }
    
    private static final Customer CUSTOMER_1 = customer("c1", "Minsk");
    private static final Customer CUSTOMER_2 = customer("c2", "Warsaw");
    private static final Customer CUSTOMER_3 = customer("c3", "Minsk");
    private static final Customer CUSTOMER_4 = customer("c4", "Prague");

    private static final OrderItem ITEM_1  = item("Laptop", 1, 1000);
    private static final OrderItem ITEM_2  = item("Mouse", 2, 25);
    private static final OrderItem ITEM_3  = item("Book", 1, 10);
    private static final OrderItem ITEM_4  = item("Book", 1, 10);
    private static final OrderItem ITEM_5  = item("Keyboard", 1, 50);
    private static final OrderItem ITEM_6  = item("Pen", 2, 2);
    private static final OrderItem ITEM_7  = item("Notebook", 1, 5);
    private static final OrderItem ITEM_8  = item("Laptop", 1, 1000);
    private static final OrderItem ITEM_9  = item("Pen", 2, 2);
    private static final OrderItem ITEM_10 = item("Pen", 1, 2);
    private static final OrderItem ITEM_11 = item("Monitor", 1, 300);
    private static final OrderItem ITEM_12 = item("Book", 3, 10);

    private static final Order ORDER_1  = order("o1", OrderStatus.DELIVERED, CUSTOMER_1, ITEM_1);
    private static final Order ORDER_2  = order("o2", OrderStatus.DELIVERED, CUSTOMER_1, ITEM_2, ITEM_3);
    private static final Order ORDER_3  = order("o3", OrderStatus.CANCELLED, CUSTOMER_1, ITEM_4);
    private static final Order ORDER_4  = order("o4", OrderStatus.DELIVERED, CUSTOMER_2, ITEM_5);
    private static final Order ORDER_5  = order("o5", OrderStatus.SHIPPED, CUSTOMER_2, ITEM_6);
    private static final Order ORDER_6  = order("o6", OrderStatus.DELIVERED, CUSTOMER_3, ITEM_7, ITEM_8);
    private static final Order ORDER_7  = order("o7", OrderStatus.DELIVERED, CUSTOMER_4, ITEM_9);
    private static final Order ORDER_8  = order("o8", OrderStatus.DELIVERED, CUSTOMER_1, ITEM_10);
    private static final Order ORDER_9  = order("o7", OrderStatus.DELIVERED, CUSTOMER_1, ITEM_11);
    private static final Order ORDER_10 = order("o8", OrderStatus.DELIVERED, CUSTOMER_1, ITEM_12);


    @BeforeEach
    void setUp() {
        orders = List.of(ORDER_1, ORDER_2, ORDER_3, ORDER_4, ORDER_5, ORDER_6, ORDER_7, ORDER_8, ORDER_9, ORDER_10);
        service = new OrderAnalysisService();
    }


    @Test
    void getUniqueCities() {
        List<Order> orders1 = List.of();
        Set<String> result1 = service.getUniqueCities(orders1);
        Assertions.assertEquals(Set.of(), result1);

        List<Order> orders2 = orders;
        Set<String> result2 = service.getUniqueCities(orders2);
        Assertions.assertEquals(Set.of("Warsaw", "Minsk", "Prague"), result2);
    }


    @Test
    void getTotalIncomeFromDeliveredOrders() {
        List<Order> orders1 = List.of();
        Double result1 = service.getTotalIncomeFromDeliveredOrders(orders1);
        Assertions.assertEquals(0.0, result1);

        List<Order> orders2 = orders;
        Double result2 = service.getTotalIncomeFromDeliveredOrders(orders2);
        Assertions.assertEquals(2451.0, result2);
    }


    @Test
    void getMostPopularProduct() {
        List<Order> orders1 = List.of();
        Optional<String> product1 = service.getMostPopularProduct(orders1);
        Assertions.assertFalse(product1.isPresent());

        List<Order> orders2 = orders;
        Optional<String> product2 = service.getMostPopularProduct(orders2);
        Assertions.assertTrue(product2.isPresent());
        Assertions.assertEquals("Book", product2.get());
    }


    @Test
    void getMostPopularProductList() {
        List<Order> orders1 = List.of();
        List<String> products1 = service.getMostPopularProductList(orders1);
        Assertions.assertTrue(products1.isEmpty());

        List<Order> orders2 = orders;
        List<String> products2 = service.getMostPopularProductList(orders2);
        Assertions.assertFalse(products2.isEmpty());
        Assertions.assertEquals(List.of("Book", "Pen"), products2);
    }


    @Test
    void getAverageCheckFromDeliveredOrders() {
        List<Order> orders1 = List.of();
        Double averageCheck1 = service.getAverageCheckFromDeliveredOrders(orders1);
        Assertions.assertEquals(0.0, averageCheck1);

        List<Order> orders2 = orders;
        Double averageCheck2 = service.getAverageCheckFromDeliveredOrders(orders2);
        Assertions.assertEquals(306.375, averageCheck2);
    }


    @Test
    void getCustomersWithMoreThanFiveOrders() {
        List<Order> orders1 = List.of();
        List<Customer> customers1 = service.getCustomersWithMoreThanFiveOrders(orders1);
        Assertions.assertEquals(0, customers1.size());

        List<Order> orders2 = orders;
        List<Customer> customers2 = service.getCustomersWithMoreThanFiveOrders(orders2);
        Assertions.assertEquals(1, customers2.size());
        Assertions.assertEquals(CUSTOMER_1, customers2.getFirst());
    }
}