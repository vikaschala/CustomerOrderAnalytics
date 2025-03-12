package com.dev.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.analytics.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}