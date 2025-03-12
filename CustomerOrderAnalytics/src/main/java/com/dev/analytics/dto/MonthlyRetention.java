package com.dev.analytics.dto;

import java.time.YearMonth;

public class MonthlyRetention {
    private YearMonth month;
    private int returningCustomers;
    private int totalCustomers;
    private double retentionRate;

    public MonthlyRetention(YearMonth month, int returningCustomers, int totalCustomers, double retentionRate) {
        this.month = month;
        this.returningCustomers = returningCustomers;
        this.totalCustomers = totalCustomers;
        this.retentionRate = retentionRate;
    }

    // Getters
    public YearMonth getMonth() { return month; }
    public int getReturningCustomers() { return returningCustomers; }
    public int getTotalCustomers() { return totalCustomers; }
    public double getRetentionRate() { return retentionRate; }
}