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
    @Schema(description = "temperature", example = "12.5", minimum = "-99", maximum = "99")
    @Range(min = -99, max = 99, message = "unacceptable temperature")
    private Double temperature;
    @Schema(description = "humidity", example = "12.5", minimum = "0", maximum = "100")
    @Range(min = 0, max = 100, message = "unacceptable humidity")
    private Double humidity;
    @Schema(description = "pressure", example = "12.5", minimum = "650", maximum = "850")
    @Range(min = 650, max = 850, message = "unacceptable pressure")
    private Double pressure;
    @Schema(description = "temperature", example = "12.5", minimum = "0", maximum = "150")
    @Range(min = 0, max = 150, message = "unacceptable wind speed")
    private Double windSpeed;
    @Schema(description = "temperature", example = "12", minimum = "0", maximum = "359")
    @Range(min = 0, max = 359, message = "unacceptable wind direction")
    private Integer windDirection;
}
