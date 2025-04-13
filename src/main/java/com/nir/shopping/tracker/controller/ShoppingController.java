package com.nir.shopping.tracker.controller;

import com.nir.shopping.tracker.domain.ShoppingList;
import com.nir.shopping.tracker.domain.ShoppingListItem;
import com.nir.shopping.tracker.dto.MonthlyReport;
import com.nir.shopping.tracker.service.ShoppingListService;
import com.nir.shopping.tracker.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    private final ShoppingListService service;
    private final JwtUtil jwtUtil;

    public ShoppingController(ShoppingListService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        return Long.parseLong(jwtUtil.extractUserId(token));
    }

    @PostMapping("/list")
    public ResponseEntity<?> createOrUpdateList(@RequestBody ShoppingList list, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        list.setUser(null); // prevent user from being injected from frontend
        return ResponseEntity.ok(service.createOrUpdateList(userId, list));
    }

    @PostMapping("/item")
    public ResponseEntity<?> createOrUpdateItem(@RequestParam Long listId, @RequestBody ShoppingListItem item, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(service.createOrUpdateItem(listId, item, userId));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getShoppingLists(@RequestParam(defaultValue = "false") boolean detail, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        return ResponseEntity.ok(service.getLists(userId, detail));
    }

    @GetMapping("/total")
    public ResponseEntity<MonthlyReport> getTotalInRange(@RequestParam String start, @RequestParam String end, HttpServletRequest request) {
        Long userId = getUserIdFromRequest(request);
        LocalDateTime s = LocalDateTime.parse(start);
        LocalDateTime e = LocalDateTime.parse(end);
        return ResponseEntity.ok(service.calculateTotalInRange(userId, s, e));
    }

    @PostMapping("/finalize/{listId}/list")
    public ResponseEntity<?> finalizeBill(@PathVariable Long listId) {
        service.finalizeBill(listId);
        return ResponseEntity.ok("Bill finalization triggered.");
    }

}
