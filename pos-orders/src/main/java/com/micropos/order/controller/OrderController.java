package com.micropos.order.controller;

import com.micropos.api.controller.OrdersApi;
import com.micropos.api.dto.OrderDto;
import com.micropos.api.dto.OrderOutlineDto;
import com.micropos.api.dto.PaymentDto;
import com.micropos.order.mapper.OrderMapper;
import com.micropos.order.model.Order;
import com.micropos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController implements OrdersApi {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderService orderService;

    @Override
    public ResponseEntity<OrderDto> getOrderById(String orderId) {
        Order order = orderService.orderById(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(orderMapper.toOrderDto(order));
    }

    @Override
    public ResponseEntity<List<OrderOutlineDto>> getOrders() {
        List<Order> orders = orderService.orders();
        return ResponseEntity.ok().body(orderMapper.toOrderOutlines(orders));
    }

    @Override
    public ResponseEntity<String> createOrder(PaymentDto paymentDto) {
        String orderId = orderService.createOrder(paymentDto.getTotal(), paymentDto.getItems());
        if (orderId != null) {
            return ResponseEntity.ok().body(orderId);
        }
        return ResponseEntity.badRequest().build();
    }
}
