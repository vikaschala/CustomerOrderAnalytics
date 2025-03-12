package com.dev.analytics.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.analytics.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}