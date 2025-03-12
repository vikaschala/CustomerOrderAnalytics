package com.dev.analytics.dto;

import java.math.BigDecimal;

public class CustomerRevenue {
    private Long customerId;
    private String customerName;
    private BigDecimal totalSpent;
    private BigDecimal refundedAmount;
    private BigDecimal netRevenue;

    public CustomerRevenue(Long customerId, BigDecimal totalSpent, BigDecimal refundedAmount, BigDecimal netRevenue) {
        this.customerId = customerId;
        this.totalSpent = totalSpent;
        this.refundedAmount = refundedAmount;
        this.netRevenue = netRevenue;
    }

    // Getters and Setters
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public BigDecimal getTotalSpent() { return totalSpent; }
    public void setTotalSpent(BigDecimal totalSpent) { this.totalSpent = totalSpent; }
    public BigDecimal getRefundedAmount() { return refundedAmount; }
    public void setRefundedAmount(BigDecimal refundedAmount) { this.refundedAmount = refundedAmount; }
    public BigDecimal getNetRevenue() { return netRevenue; }
    public void setNetRevenue(BigDecimal netRevenue) { this.netRevenue = netRevenue; }
}