package com.micropos.order.service;

import com.micropos.order.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> orders();

    Order orderById(String orderId);

    boolean createOrder();
}
