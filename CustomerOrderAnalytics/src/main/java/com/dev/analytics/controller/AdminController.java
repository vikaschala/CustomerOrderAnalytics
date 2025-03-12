package com.dev.analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dev.analytics.entity.Order;
import com.dev.analytics.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/orders/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestBody String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}