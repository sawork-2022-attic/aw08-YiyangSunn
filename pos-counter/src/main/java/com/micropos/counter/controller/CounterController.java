package com.micropos.counter.controller;

import com.micropos.api.controller.CounterApi;
import com.micropos.api.dto.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequestMapping("/api")
public class CounterController implements CounterApi {

    @Override
    public ResponseEntity<Double> checkout(List<ItemDto> itemDtoList) {
        double total = 0;
        for (ItemDto itemDto : itemDtoList) {
            total += itemDto.getQuantity() * itemDto.getPrice();
        }
        return ResponseEntity.ok().body(total);
    }
}
