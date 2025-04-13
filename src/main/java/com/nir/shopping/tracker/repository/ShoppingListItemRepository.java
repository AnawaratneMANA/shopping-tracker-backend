package com.nir.shopping.tracker.repository;

import com.nir.shopping.tracker.domain.ShoppingListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListItemRepository extends JpaRepository<ShoppingListItem, Long> {
    List<ShoppingListItem> findAllByShoppingListShoppingListId(Long shoppingListId);
}