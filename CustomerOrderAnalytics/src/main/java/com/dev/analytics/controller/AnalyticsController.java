package com.dev.analytics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.analytics.dto.*;
import com.dev.analytics.service.AnalyticsService;

import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/top-customers")
    public List<CustomerRevenue> getTopProfitableCustomers() {
        return analyticsService.getTopProfitableCustomers();
    }

    @GetMapping("/retention")
    public List<MonthlyRetention> getMonthlyRetentionRates() {
        return analyticsService.getMonthlyRetentionRates();
    }

    @GetMapping("/processing-times")
    public List<OrderProcessingTime> getLongestProcessingTimes() {
        return analyticsService.getLongestProcessingTimes();
    }

    @GetMapping("/category-aov")
    public List<CategoryAOV> getCategoryAOVExtremes() {
        return analyticsService.getCategoryAOVExtremes();
    }

    @GetMapping("/seasonal-revenue")
    public List<SeasonalRevenue> getSeasonalRevenue() {
        return analyticsService.getSeasonalRevenue();
    }

    @GetMapping("/order-sizes")
    public List<OrderSizeCount> getOrderSizeDistribution() {
        return analyticsService.getOrderSizeDistribution();
    }
}