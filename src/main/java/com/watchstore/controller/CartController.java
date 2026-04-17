package com.watchstore.controller;

import com.watchstore.dto.CartDTO;
import com.watchstore.model.User;
import com.watchstore.service.CartService;
import com.watchstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@org.springframework.transaction.annotation.Transactional
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    private User getCurrentUser() {
        org.springframework.security.core.Authentication authentication = 
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            throw new RuntimeException("User not authenticated");
        }

        Object principal = authentication.getPrincipal();
        String email;
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }
        
        return userService.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart() {
        try {
            return ResponseEntity.ok(cartService.getCartByUser(getCurrentUser()));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CartDTO> addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addToCart(getCurrentUser(), productId, quantity));
    }

    @PostMapping("/remove/{itemId}")
    public ResponseEntity<CartDTO> removeItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(cartService.removeItem(getCurrentUser(), itemId));
    }

    @PostMapping("/update/{itemId}")
    public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long itemId, @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateQuantity(getCurrentUser(), itemId, quantity));
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart(getCurrentUser());
        return ResponseEntity.ok().build();
    }
}
