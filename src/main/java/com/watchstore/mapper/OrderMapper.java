package com.watchstore.mapper;

import com.watchstore.dto.OrderDTO;
import com.watchstore.dto.OrderItemDTO;
import com.watchstore.model.Order;
import com.watchstore.model.OrderItem;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setItems(order.getItems().stream().map(this::itemToDTO).collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDTO itemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName());
        dto.setQuantity(item.getQuantity());
        dto.setPriceAtPurchase(item.getPriceAtPurchase());
        return dto;
    }
}
