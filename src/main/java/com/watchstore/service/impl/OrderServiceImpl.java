package com.watchstore.service.impl;

import com.watchstore.dto.OrderDTO;
import com.watchstore.enums.OrderStatus;
import com.watchstore.exception.ResourceNotFoundException;
import com.watchstore.mapper.OrderMapper;
import com.watchstore.model.*;
import com.watchstore.repository.CartRepository;
import com.watchstore.repository.OrderRepository;
import com.watchstore.service.CartService;
import com.watchstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(User user) {
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        
        if (cart.getItems().isEmpty()) {
            throw new ResourceNotFoundException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(cart.getTotalAmount());

        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
        }

        Order savedOrder = orderRepository.save(order);
        
        // Clear cart
        cart.getItems().clear();
        cart.setTotalAmount(java.math.BigDecimal.ZERO);
        cartRepository.save(cart);

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public List<OrderDTO> getUserOrders(User user) {
        return orderRepository.findByUser(user).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
