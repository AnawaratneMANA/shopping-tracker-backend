package com.nir.shopping.tracker.repository;

import com.nir.shopping.tracker.domain.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
    @Query("SELECT s FROM ShoppingList s WHERE s.user.userId = :userId")
    List<ShoppingList> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT DISTINCT s FROM ShoppingList s LEFT JOIN FETCH s.items WHERE s.user.userId = :userId")
    List<ShoppingList> findAllWithItemsByUserId(@Param("userId") Long userId);

    Optional<ShoppingList> findByShoppingListIdAndUserUserId(Long id, Long userId);

    @Query("SELECT DISTINCT s FROM ShoppingList s LEFT JOIN FETCH s.items WHERE s.user.userId = :userId AND s.createdDate BETWEEN :start AND :end")
    List<ShoppingList> findWithinPeriod(@Param("userId") Long userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}