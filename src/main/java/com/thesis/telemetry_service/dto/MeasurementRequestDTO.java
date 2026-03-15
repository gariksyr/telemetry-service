package com.thesis.telemetry_service.dto;

import com.thesis.telemetry_service.util.ValidImo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MeasurementRequestDTO {
    @Schema(description = "Unique 7-digit vessel code", example = "9311622", requiredMode = Schema.RequiredMode.REQUIRED)
    @ValidImo(message = "invalid IMO")
    private String vesselImo;
    @Schema(description = "Latitude (WGS 84)", example = "59.9311", minimum = "-90", maximum = "90")
    @Range(min = -90, max = 90, message = "invalid latitude")
    private Double latitude;
    @Schema(description = "Longitude (WGS 84)", example = "30.3609", minimum = "-180", maximum = "180")
    @Range(min = -180, max = 180, message = "invalid longitude")
    private Double longitude;
    @Schema(description = "Vessel speed in knots", example = "12.5", minimum = "0", maximum = "70")
    @Range(min = 0, max = 70, message = "unacceptable speed")
    private Double speed;
}
