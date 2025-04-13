package com.nir.shopping.tracker.dto;

import com.nir.shopping.tracker.domain.ShoppingList;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyReport {
    private final List<ShoppingList> list;
    private final BigDecimal total;

    public MonthlyReport(List<ShoppingList> list, BigDecimal total) {
        this.list = list;
        this.total = total;
    }

    public List<ShoppingList> getList() {
        return list;
    }

    public BigDecimal getTotal() {
        return total;
    }
}