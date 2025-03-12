package com.dev.analytics.dto;

import java.math.BigDecimal;

public class SeasonalRevenue {
    private String season;
    private BigDecimal totalRevenue;

    public SeasonalRevenue(String season, BigDecimal totalRevenue) {
        this.season = season;
        this.totalRevenue = totalRevenue;
    }

    public String getSeason() { return season; }
    public BigDecimal getTotalRevenue() { return totalRevenue; }
}