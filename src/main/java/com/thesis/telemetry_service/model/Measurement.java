package com.thesis.telemetry_service.model;

import com.thesis.telemetry_service.util.ValidImo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @ValidImo
    private String vesselImo;
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;
    @NotNull
    private Double speed;
    @CreationTimestamp
    private LocalDateTime timestamp;
}
