package com.dev.analytics.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dev.analytics.entity.Customer;
import com.dev.analytics.entity.Order;
import com.dev.analytics.entity.OrderItem;
import com.dev.analytics.repository.CustomerRepository;
import com.dev.analytics.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderRepository orderRepository;

    private static final String[] FIRST_NAMES = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson", "Brown", "Taylor", "Wilson"};
    private static final String[] CATEGORIES = {"Electronics", "Clothing", "Books", "Toys", "Food"};
    private static final String[] PRODUCTS = {"Laptop", "Shirt", "Novel", "Doll", "Snack"};
    private static final String[] STATUSES = {"PENDING", "SHIPPED", "DELIVERED", "CANCELED"};

    @Override
    public void run(String... args) {
       
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String name = FIRST_NAMES[i % FIRST_NAMES.length] + " " + LAST_NAMES[i % LAST_NAMES.length];
            String email = name.toLowerCase().replace(" ", ".") + "@example.com";
            customers.add(new Customer(name, email));
        }
        customerRepository.saveAll(customers);

      
        Random random = new Random();
        for (Customer customer : customers) {
            int numOrders = random.nextInt(10) + 1; 
            for (int j = 0; j < numOrders; j++) {
                LocalDateTime orderDate = LocalDateTime.now().minusMonths(random.nextInt(12))
                    .minusDays(random.nextInt(28));
                List<OrderItem> items = new ArrayList<>();
                int numItems = random.nextInt(9) + 2; 
                for (int k = 0; k < numItems; k++) {
                    int idx = random.nextInt(CATEGORIES.length);
                    String category = CATEGORIES[idx];
                    String product = PRODUCTS[idx] + " " + (k + 1);
                    int quantity = random.nextInt(5) + 1;
                    BigDecimal price = BigDecimal.valueOf(random.nextDouble() * 100 + 10);
                    items.add(new OrderItem(product, category, quantity, price));
                }
                Order order = new Order(customer, items);
                String status = STATUSES[random.nextInt(STATUSES.length)];
                order.setOrderDate(orderDate);
                order.setStatus(status);
                if ("DELIVERED".equals(status)) {
                    order.setDeliveryDate(orderDate.plusDays(random.nextInt(15) + 1));
                }
                items.forEach(item -> item.setOrder(order));
                orderRepository.save(order);
            }
        }
    }
}