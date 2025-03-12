package com.dev.analytics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.analytics.dto.*;
import com.dev.analytics.entity.Customer;
import com.dev.analytics.entity.Order;
import com.dev.analytics.entity.OrderItem;
import com.dev.analytics.repository.CustomerRepository;
import com.dev.analytics.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    // 1. Most Profitable Customers
    public List<CustomerRevenue> getTopProfitableCustomers() {
        List<Object[]> results = orderRepository.getCustomerRevenue();
        List<CustomerRevenue> revenues = results.stream()
            .map(obj -> new CustomerRevenue(
                (Long) obj[0],
                (BigDecimal) obj[1],
                (BigDecimal) obj[2],
                ((BigDecimal) obj[1]).subtract((BigDecimal) obj[2])
            ))
            .sorted(Comparator.comparing(CustomerRevenue::getNetRevenue).reversed())
            .limit(3)
            .collect(Collectors.toList());

        revenues.forEach(cr -> {
            Customer customer = customerRepository.findById(cr.getCustomerId()).orElse(null);
            if (customer != null) {
                cr.setCustomerName(customer.getName());
            }
        });
        return revenues;
    }

    // 2. Monthly Retention Rate
    public List<MonthlyRetention> getMonthlyRetentionRates() {
        List<Object[]> monthlyData = orderRepository.getMonthlyCustomers();
        Map<YearMonth, Set<Long>> customersByMonth = new HashMap<>();

        for (Object[] data : monthlyData) {
            int year = ((Number) data[0]).intValue();
            int month = ((Number) data[1]).intValue();
            Long customerId = (Long) data[2];
            YearMonth ym = YearMonth.of(year, month);
            customersByMonth.computeIfAbsent(ym, k -> new HashSet<>()).add(customerId);
        }

        List<YearMonth> sortedMonths = customersByMonth.keySet().stream()
            .sorted()
            .collect(Collectors.toList());
        List<MonthlyRetention> retentionRates = new ArrayList<>();

        for (int i = 1; i < sortedMonths.size(); i++) {
            YearMonth current = sortedMonths.get(i);
            YearMonth previous = sortedMonths.get(i - 1);
            Set<Long> currentCustomers = customersByMonth.get(current);
            Set<Long> previousCustomers = customersByMonth.get(previous);

            Set<Long> returning = new HashSet<>(currentCustomers);
            returning.retainAll(previousCustomers);

            int totalPrev = previousCustomers.size();
            double retentionRate = totalPrev > 0 ? (returning.size() * 100.0 / totalPrev) : 0;
            retentionRates.add(new MonthlyRetention(current, returning.size(), currentCustomers.size(), retentionRate));
        }
        return retentionRates;
    }

    // 3. Longest Order Processing Times
    public List<OrderProcessingTime> getLongestProcessingTimes() {
        List<Order> deliveredOrders = orderRepository.findByStatus("DELIVERED");
        return deliveredOrders.stream()
            .filter(o -> o.getDeliveryDate() != null)
            .map(o -> new OrderProcessingTime(
                o.getId(),
                o.getOrderDate(),
                o.getDeliveryDate(),
                Duration.between(o.getOrderDate(), o.getDeliveryDate()).toDays()
            ))
            .sorted(Comparator.comparing(OrderProcessingTime::getProcessingTime).reversed())
            .limit(5)
            .collect(Collectors.toList());
    }

    // 4. Best and Worst Product Categories by Average Order Value
    public List<CategoryAOV> getCategoryAOVExtremes() {
        List<Order> orders = orderRepository.findAll();
        Map<String, List<BigDecimal>> categoryTotals = new HashMap<>();

        for (Order order : orders) {
            Set<String> categories = order.getOrderItems().stream()
                .map(OrderItem::getCategory)
                .collect(Collectors.toSet());
            for (String category : categories) {
                categoryTotals.computeIfAbsent(category, k -> new ArrayList<>())
                    .add(order.getTotalAmount());
            }
        }

        List<CategoryAOV> aovs = categoryTotals.entrySet().stream()
            .map(entry -> {
                String cat = entry.getKey();
                List<BigDecimal> totals = entry.getValue();
                BigDecimal avg = totals.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(totals.size()), 2, BigDecimal.ROUND_HALF_UP);
                return new CategoryAOV(cat, avg);
            })
            .sorted(Comparator.comparing(CategoryAOV::getAvgOrderValue))
            .collect(Collectors.toList());

        if (aovs.size() >= 2) {
            return List.of(aovs.get(0), aovs.get(aovs.size() - 1));
        }
        return aovs;
    }

    // 5. Seasonal Order Patterns
    public List<SeasonalRevenue> getSeasonalRevenue() {
        List<Order> deliveredOrders = orderRepository.findByStatus("DELIVERED");
        Map<String, BigDecimal> seasonRevenue = new HashMap<>();

        for (Order order : deliveredOrders) {
            int month = order.getOrderDate().getMonthValue();
            String season = getSeason(month);
            seasonRevenue.merge(season, order.getTotalAmount(), BigDecimal::add);
        }

        return seasonRevenue.entrySet().stream()
            .map(e -> new SeasonalRevenue(e.getKey(), e.getValue()))
            .sorted(Comparator.comparing(SeasonalRevenue::getTotalRevenue).reversed())
            .collect(Collectors.toList());
    }

    private String getSeason(int month) {
        if (month == 12 || month <= 2) return "Winter";
        if (month <= 5) return "Spring";
        if (month <= 8) return "Summer";
        return "Fall";
    }

    // 6. Most Common Order Sizes
    public List<OrderSizeCount> getOrderSizeDistribution() {
        List<Order> orders = orderRepository.findAll();
        Map<String, Integer> sizeCounts = new HashMap<>();

        for (Order order : orders) {
            int itemCount = order.getOrderItems().size();
            String size = itemCount <= 2 ? "Small" : itemCount <= 5 ? "Medium" : "Large";
            sizeCounts.merge(size, 1, Integer::sum);
        }

        return sizeCounts.entrySet().stream()
            .map(e -> new OrderSizeCount(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
    }
}