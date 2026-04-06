package com.thesis.telemetry_service.kafka;

import com.thesis.telemetry_service.model.Measurement;
import com.thesis.telemetry_service.dto.MeasurementEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelemetryProducer {
    private final KafkaTemplate<String, MeasurementEvent> kafkaTemplate;
    public void sendTelemetry(Measurement measurement) {
        MeasurementEvent event = new MeasurementEvent(
                measurement.getVesselImo(),
                measurement.getLocation().getY(),
                measurement.getLocation().getX(),
                measurement.getSpeed(),
                measurement.getTimestamp()
        );

        kafkaTemplate.send("telemetry-topic", event.getVesselImo(), event);
    }
}
