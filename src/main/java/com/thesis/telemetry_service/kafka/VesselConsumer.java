package com.thesis.telemetry_service.kafka;

import com.thesis.telemetry_service.model.Imo;
import com.thesis.telemetry_service.repository.ImoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class VesselConsumer {
    private final StringRedisTemplate redisTemplate;
    private final ImoRepository imoRepository;
    @KafkaListener(topics = "vessels-topic", groupId = "telemetry-group")
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);
        redisTemplate.opsForValue().set(
                "vessel:" + message,
                "REGISTERED",
                Duration.ofHours(24)
        );
        Imo imo = new Imo();
        imo.setImo(message);
        imoRepository.save(imo);
        System.out.println("IMO " + message + " сохранен в Redis!");
    }
}
