package com.micropos.order.service;

import com.micropos.api.dto.ItemDto;
import com.micropos.order.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> orders();

    Order orderById(String orderId);

    String createOrder(double total, List<ItemDto> items);
}
