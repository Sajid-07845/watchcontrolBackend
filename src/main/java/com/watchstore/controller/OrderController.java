package com.watchstore.controller;

import com.watchstore.dto.OrderDTO;
import com.watchstore.model.User;
import com.watchstore.service.OrderService;
import com.watchstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // Temporary: Hardcoding user ID 1 for now
    private User getCurrentUser() {
        return userService.getUserById(1L);
    }

    @PostMapping("/place")
    public ResponseEntity<OrderDTO> placeOrder() {
        return ResponseEntity.ok(orderService.placeOrder(getCurrentUser()));
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        return ResponseEntity.ok(orderService.getUserOrders(getCurrentUser()));
    }
}
