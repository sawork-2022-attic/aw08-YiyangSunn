package com.micropos.delivery.controller;

import com.micropos.api.controller.DeliveryApi;
import com.micropos.api.dto.DeliveryDto;
import com.micropos.delivery.mapper.DeliveryMapper;
import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryController implements DeliveryApi {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private DeliveryMapper deliveryMapper;

    @Override
    public ResponseEntity<DeliveryDto> getDeliveryById(String deliveryId) {
        Delivery delivery = deliveryService.findDelivery(deliveryId);
        if (delivery != null) {
            return ResponseEntity.ok().body(deliveryMapper.toDeliveryDto(delivery));
        }
        return ResponseEntity.notFound().build();
    }
}
