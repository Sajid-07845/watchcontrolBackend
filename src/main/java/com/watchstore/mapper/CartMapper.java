package com.watchstore.mapper;

import com.watchstore.dto.CartDTO;
import com.watchstore.dto.CartItemDTO;
import com.watchstore.model.Cart;
import com.watchstore.model.CartItem;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalAmount(cart.getTotalAmount());
        dto.setItems(cart.getItems().stream().map(this::itemToDTO).collect(Collectors.toList()));
        return dto;
    }

    private CartItemDTO itemToDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setId(item.getId());
        if (item.getProduct() != null) {
            dto.setProductId(item.getProduct().getId());
            dto.setProductName(item.getProduct().getName());
            dto.setProductImageUrl(item.getProduct().getImageUrl());
            dto.setUnitPrice(item.getProduct().getPrice());
        }
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
