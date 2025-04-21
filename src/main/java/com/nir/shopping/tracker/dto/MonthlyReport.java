package com.nir.shopping.tracker.dto;

import com.nir.shopping.tracker.domain.ShoppingList;

import java.math.BigDecimal;
import java.util.List;

public class MonthlyReport {
    private List<ShoppingListDto> list;
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public List<ShoppingListDto> getList() {
        return list;
    }

    public void setList(List<ShoppingListDto> list) {
        this.list = list;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}