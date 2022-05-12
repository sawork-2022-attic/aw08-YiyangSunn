package com.micropos.delivery.service;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.DeliveryInfo;
import com.micropos.delivery.model.DeliveryPhase;

import java.util.List;

public interface DeliveryService {

    void createDelivery(DeliveryInfo deliveryInfo);

    List<Delivery> findDeliveries();

    Delivery findDelivery(String deliveryId);

    boolean addDeliveryPhase(String deliveryId, DeliveryPhase phase);

}
