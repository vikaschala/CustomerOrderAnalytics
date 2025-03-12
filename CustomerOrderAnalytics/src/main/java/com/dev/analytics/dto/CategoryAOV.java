package com.dev.analytics.dto;

import java.math.BigDecimal;

public class CategoryAOV {
    private String category;
    private BigDecimal avgOrderValue;

    public CategoryAOV(String category, BigDecimal avgOrderValue) {
        this.category = category;
        this.avgOrderValue = avgOrderValue;
    }

    public String getCategory() { return category; }
    public BigDecimal getAvgOrderValue() { return avgOrderValue; }
}