package com.watchstore.service;

import com.watchstore.dto.CartDTO;
import com.watchstore.model.User;

public interface CartService {
    CartDTO getCartByUser(User user);
    CartDTO addToCart(User user, Long productId, int quantity);
    CartDTO updateQuantity(User user, Long itemId, int quantity);
    CartDTO removeItem(User user, Long itemId);
    void clearCart(User user);
}
