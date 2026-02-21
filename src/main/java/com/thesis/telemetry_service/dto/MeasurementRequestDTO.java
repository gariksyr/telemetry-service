package com.thesis.telemetry_service.dto;

import com.thesis.telemetry_service.util.ValidImo;
import lombok.Data;

@Data
public class MeasurementRequestDTO {
    @ValidImo
    private String vesselImo;
    private Double latitude;
    private Double longitude;
    private Double speed;
}
