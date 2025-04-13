package com.nir.shopping.tracker.service;

import com.nir.shopping.tracker.domain.ShoppingList;
import com.nir.shopping.tracker.domain.ShoppingListItem;
import com.nir.shopping.tracker.dto.MonthlyReport;
import com.nir.shopping.tracker.repository.ShoppingListItemRepository;
import com.nir.shopping.tracker.repository.ShoppingListRepository;
import com.nir.shopping.tracker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class ShoppingListService {
    private static final Logger log = LoggerFactory.getLogger(ShoppingListService.class);
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListItemRepository itemRepository;
    private final UserRepository userRepository;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, ShoppingListItemRepository itemRepository, UserRepository userRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method to create the shopping list.
     *
     * @param userId       > User ID, Extracted from the controller level.
     * @param shoppingList > Shopping list
     * @return {@link ShoppingList}
     */
    public ShoppingList createOrUpdateList(Long userId, ShoppingList shoppingList) {
        log.debug("Creating shopping list: {}", shoppingList.getListName());
        if (shoppingList.getShoppingListId() == null || shoppingList.getUser() == null) {
            shoppingList.setUser(userRepository.findById(userId).orElseThrow());
        }
        if (shoppingList.getItems() != null) {
            for (ShoppingListItem item : shoppingList.getItems()) {
                item.setShoppingList(shoppingList);
            }
        }
        return shoppingListRepository.save(shoppingList);
    }

    /**
     * Method is to create or update the shopping list items.
     *
     * @param listId > Shopping list id which the item belongs to.
     * @param item   > Item
     * @return {@link ShoppingListItem}
     */
    public ShoppingListItem createOrUpdateItem(Long listId, ShoppingListItem item, Long userId) {
        ShoppingList list = shoppingListRepository.findByShoppingListIdAndUserUserId(listId, userId)
                .orElseThrow(() -> new IllegalArgumentException("List not found or does not belong to user"));
        item.setShoppingList(list);
        return itemRepository.save(item);
    }

    /**
     * Method is to get the list for users, can be fetched in detailed form or brief.
     *
     * @param userId > User ID
     * @param detail > Detail information
     * @return {@link List<ShoppingList>}
     */
    public List<ShoppingList> getLists(Long userId, boolean detail) {
        log.debug("Get lists for the user: {}", userId);
        return detail ? shoppingListRepository.findAllWithItemsByUserId(userId)
                : shoppingListRepository.findAllByUserId(userId);
    }

    /**
     * Method to calculate total expenses.
     *
     * @param userId > User ID
     * @param start  > Start Date
     * @param end    > End Date
     * @return {@link BigDecimal}
     */
    public MonthlyReport calculateTotalInRange(Long userId, LocalDateTime start, LocalDateTime end) {
        List<ShoppingList> lists = shoppingListRepository.findWithinPeriod(userId, start, end);
        BigDecimal overallTotal = lists.stream()
                .map(ShoppingList::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new MonthlyReport(lists, overallTotal);
    }


    /**
     * Async method to prepare the summary for each shopping items after the checking out.
     *
     * @param shoppingListId > Shopping Item ID
     */
    @Async
    public void finalizeBill(Long shoppingListId) {
        ShoppingList list = shoppingListRepository.findById(shoppingListId).orElseThrow();
        BigDecimal total = BigDecimal.ZERO;
        for (ShoppingListItem item : itemRepository.findAllByShoppingListShoppingListId(shoppingListId)
                .stream().filter(ShoppingListItem::isCheckedOut).toList()) {
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setTotal(itemTotal);
            total = total.add(itemTotal);
            itemRepository.save(item);
        }
        list.setTotal(total);
        shoppingListRepository.save(list);
    }
}
