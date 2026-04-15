package com.thesis.telemetry_service.kafka;

import com.thesis.telemetry_service.service.ImoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VesselConsumer {
    private final ImoService imoService;
    @KafkaListener(topics = "vessels-topic", groupId = "telemetry-group")
    public void listen(String message) {
        log.info("Received message from Kafka: {}", message);
        imoService.saveEverywhereByImo(message);
        System.out.println("IMO " + message + " сохранен в Redis!");
    }
    @KafkaListener(topics = "vessels-deletion", groupId = "telemetry-group")
    public void listenDeletion(String message) {
        log.info("Получен сигнал на удаление кэша судна: {}", message);
        imoService.deleteFromEverywhereByImo(message);
        log.info("Судно {} полностью удалено из локальных кэшей телеметрии", message);
    }
}
