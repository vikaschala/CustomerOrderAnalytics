package com.dev.analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dev.analytics.entity.Customer;
import com.dev.analytics.entity.Order;
import com.dev.analytics.entity.OrderItem;
import com.dev.analytics.service.CustomerService;
import com.dev.analytics.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @PostMapping("/orders")
    public Order placeOrder(@PathVariable Long customerId, @RequestBody List<OrderItem> orderItems) {
        return orderService.placeOrder(customerId, orderItems);
    }

    @GetMapping("/orders")
    public List<Order> getOrders(@PathVariable Long customerId) {
        return orderService.getOrdersByCustomer(customerId);
    }

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/orders/{orderId}")
    public void cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
    }
}