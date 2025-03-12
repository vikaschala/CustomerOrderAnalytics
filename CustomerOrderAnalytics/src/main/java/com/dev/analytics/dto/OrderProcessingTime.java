package com.dev.analytics.dto;

import java.time.LocalDateTime;

public class OrderProcessingTime {
    private Long orderId;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private long processingTime;

    public OrderProcessingTime(Long orderId, LocalDateTime orderDate, LocalDateTime deliveryDate, long processingTime) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.processingTime = processingTime;
    }

    // Getters
    public Long getOrderId() { return orderId; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public long getProcessingTime() { return processingTime; }
}