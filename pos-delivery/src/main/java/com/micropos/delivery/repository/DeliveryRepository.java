package com.micropos.delivery.repository;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.DeliveryPhase;

import java.util.List;

public interface DeliveryRepository {

    boolean saveDelivery(Delivery delivery);

    List<Delivery> allDeliveries();

    Delivery findDeliveryById(String deliveryId);

    boolean addDeliveryPhase(String deliveryId, DeliveryPhase phase);

}
