package com.dev.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dev.analytics.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByStatus(String status);

    @Query("SELECT o.customer.id, " +
           "SUM(CASE WHEN o.status = 'DELIVERED' THEN o.totalAmount ELSE 0 END), " +
           "SUM(CASE WHEN o.status = 'CANCELED' THEN o.totalAmount ELSE 0 END) " +
           "FROM Order o " +
           "GROUP BY o.customer.id")
    List<Object[]> getCustomerRevenue();

    @Query("SELECT FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate), o.customer.id " +
           "FROM Order o " +
           "WHERE o.status != 'CANCELED' " +
           "GROUP BY FUNCTION('YEAR', o.orderDate), FUNCTION('MONTH', o.orderDate), o.customer.id")
    List<Object[]> getMonthlyCustomers();
}