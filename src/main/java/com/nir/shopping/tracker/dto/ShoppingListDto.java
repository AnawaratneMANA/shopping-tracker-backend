package com.nir.shopping.tracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ShoppingListDto {
    private Long id;
    private String listName;
    private BigDecimal total;
    private LocalDateTime createdDate;
    private List<ShoppingListItemDto> items;

    public ShoppingListDto() {
    }

    public ShoppingListDto(
            Long id, String listName,
            BigDecimal total, LocalDateTime createdDate, List<ShoppingListItemDto> items) {
        this.id = id;
        this.listName = listName;
        this.total = total;
        this.createdDate = createdDate;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<ShoppingListItemDto> getItems() {
        return items;
    }

    public void setItems(List<ShoppingListItemDto> items) {
        this.items = items;
    }
}
