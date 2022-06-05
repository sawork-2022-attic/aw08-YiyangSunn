package com.micropos.order.mapper;

import com.micropos.api.dto.OrderDto;
import com.micropos.api.dto.OrderOutlineDto;
import com.micropos.order.model.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toOrderDto(Order order);

    OrderOutlineDto toOrderOutlineDto(Order order);

    List<OrderOutlineDto> toOrderOutlines(List<Order> orders);
}
