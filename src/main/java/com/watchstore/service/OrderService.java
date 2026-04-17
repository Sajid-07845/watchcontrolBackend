package com.watchstore.service;

import com.watchstore.dto.OrderDTO;
import com.watchstore.model.User;
import java.util.List;

public interface OrderService {
    OrderDTO placeOrder(User user);
    List<OrderDTO> getUserOrders(User user);
}
