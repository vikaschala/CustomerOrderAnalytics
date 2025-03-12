package com.dev.analytics.dto;

public class OrderSizeCount {
    private String size;
    private int count;

    public OrderSizeCount(String size, int count) {
        this.size = size;
        this.count = count;
    }

    // Getters
    public String getSize() { return size; }
    public int getCount() { return count; }
}