package com.micropos.delivery.repository;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.DeliveryPhase;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class SimpleDeliveryRepository implements DeliveryRepository {

    private final Map<String, Delivery> table = new ConcurrentHashMap<>();

    @Override
    public boolean saveDelivery(Delivery delivery) {
        table.put(delivery.getDeliveryId(), delivery);
        return true;
    }

    @Override
    public List<Delivery> allDeliveries() {
        return new ArrayList<>(table.values());
    }

    @Override
    public Delivery findDeliveryById(String deliveryId) {
        return table.getOrDefault(deliveryId, null);
    }

    @Override
    public boolean addDeliveryPhase(String deliveryId, DeliveryPhase phase) {
        Delivery delivery = table.getOrDefault(deliveryId, null);
        if (delivery == null) {
            return false;
        }
        delivery.getPhases().add(phase);
        return true;
    }
}
