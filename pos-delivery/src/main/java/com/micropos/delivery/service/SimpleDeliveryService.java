package com.micropos.delivery.service;

import com.micropos.delivery.model.Delivery;
import com.micropos.delivery.model.DeliveryInfo;
import com.micropos.delivery.model.DeliveryPhase;
import com.micropos.delivery.repository.DeliveryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class SimpleDeliveryService implements DeliveryService {

    private final Random random = new Random();

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public void createDelivery(DeliveryInfo deliveryInfo) {
        log.info("Create new Delivery: " + deliveryInfo);
        Delivery delivery = new Delivery();
        delivery.setOrderId(deliveryInfo.getOrderId());
        delivery.setDeliveryId(deliveryInfo.getDeliveryId());
        delivery.setCarrier(deliveryInfo.getCarrier());
        delivery.setPhases(randomDeliveryPhases());
        if (!deliveryRepository.saveDelivery(delivery)) {
            log.error("Failed to save delivery: " + delivery);
        }
    }

    @Override
    public List<Delivery> findDeliveries() {
        return deliveryRepository.allDeliveries();
    }

    @Override
    public Delivery findDelivery(String deliveryId) {
        return deliveryRepository.findDeliveryById(deliveryId);
    }

    @Override
    public boolean addDeliveryPhase(String deliveryId, DeliveryPhase phase) {
        if (deliveryId == null || phase == null) {
            return false;
        }
        return deliveryRepository.addDeliveryPhase(deliveryId, phase);
    }

    private List<DeliveryPhase> randomDeliveryPhases() {
        long timeMillis = System.currentTimeMillis();
        List<DeliveryPhase> phases = new ArrayList<>(List.of(new DeliveryPhase(timeMillis, "您的订单已被接收，正在等待商家发货")));
        if (random.nextDouble() < 0.8) {
            timeMillis += random.nextDouble() * 1800 * 1000;
            phases.add(new DeliveryPhase(timeMillis, "商家已发货，正在等待揽收"));
            if (random.nextDouble() < 0.7) {
                timeMillis += random.nextDouble() * 6 * 3600 * 1000;
                phases.add(new DeliveryPhase(timeMillis, "货物已被揽收，正在运往南大仙林校区"));
                if (random.nextDouble() < 0.6) {
                    timeMillis += random.nextDouble() * 24 * 3600 * 1000;
                    phases.add(new DeliveryPhase(timeMillis, "包裹已送达南大仙林校区6栋菜根谭快递点，正在等待分拣"));
                    if (random.nextDouble() < 0.5) {
                        timeMillis += random.nextDouble() * 12 * 3600 * 1000;
                        phases.add(new DeliveryPhase(timeMillis, "分拣完毕，等待签收"));
                    }
                }
            }
        }
        return phases;
    }
}
