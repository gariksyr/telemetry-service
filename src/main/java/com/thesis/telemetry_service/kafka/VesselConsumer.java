package com.thesis.telemetry_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VesselConsumer {

    @KafkaListener(topics = "vessels-topic", groupId = "telemetry-group")
    public void listen(String message) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("RECEIVED FROM KAFKA: " + message);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("Received message from Kafka: {}", message);
        // Тут мы будем сохранять IMO в Redis
    }
}
