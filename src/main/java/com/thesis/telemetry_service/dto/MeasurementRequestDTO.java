package com.thesis.telemetry_service.dto;

import com.thesis.telemetry_service.util.ValidImo;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MeasurementRequestDTO {
    @ValidImo(message = "invalid IMO")
    private String vesselImo;
    @Range(min = -90, max = 90, message = "invalid latitude")
    private Double latitude;
    @Range(min = -180, max = 180, message = "invalid longitude")
    private Double longitude;
    @Range(min = 0, max = 70, message = "unacceptable speed")
    private Double speed;
}
