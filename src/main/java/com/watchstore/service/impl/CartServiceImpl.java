package com.watchstore.service.impl;

import com.watchstore.dto.CartDTO;
import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.mapper.CartMapper;
import com.watchstore.model.*;
import com.watchstore.repository.CartRepository;
import com.watchstore.repository.ProductRepository;
import com.watchstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartMapper cartMapper;

    @Override
    public CartDTO getCartByUser(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
        return cartMapper.toDTO(cart);
    }

    @Override
    public CartDTO addToCart(User user, Long productId, int quantity) {
        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.addItem(newItem);
        }

        recalculateTotal(cart);
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartDTO updateQuantity(User user, Long itemId, int quantity) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (quantity <= 0) {
            return removeItem(user, itemId);
        }

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        item.setQuantity(quantity);
        recalculateTotal(cart);
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public CartDTO removeItem(User user, Long itemId) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        
        cart.getItems().removeIf(item -> item.getId().equals(itemId));
        
        recalculateTotal(cart);
        return cartMapper.toDTO(cartRepository.save(cart));
    }

    @Override
    public void clearCart(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        cart.getItems().clear();
        cart.setTotalAmount(BigDecimal.ZERO);
        cartRepository.save(cart);
    }

    private void recalculateTotal(Cart cart) {
        BigDecimal total = cart.getItems().stream()
                .filter(item -> item.getProduct() != null && item.getProduct().getPrice() != null)
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);
    }
}
