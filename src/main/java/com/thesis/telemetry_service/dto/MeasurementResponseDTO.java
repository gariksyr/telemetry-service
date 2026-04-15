package com.thesis.telemetry_service.dto;

import lombok.Data;

@Data
public class MeasurementResponseDTO {
    private Long id;
    private String vesselImo;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private Double windSpeed;
    private Integer windDirection;
}
