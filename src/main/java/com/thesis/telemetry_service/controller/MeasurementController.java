package com.thesis.telemetry_service.controller;

import com.thesis.telemetry_service.dto.MeasurementRequestDTO;
import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;
    @PostMapping()
    public ResponseEntity<MeasurementResponseDTO> addMeasurement(@Valid @RequestBody  MeasurementRequestDTO measurement){
        return ResponseEntity.ok(measurementService.addMeasurement(measurement));
    }
    @GetMapping
    public ResponseEntity<Page<MeasurementResponseDTO>> findAll(@RequestParam(defaultValue = "0")  Integer page, @RequestParam(defaultValue = "20") Integer size){
        return ResponseEntity.ok(measurementService.findAll(page, size));
    }
    @GetMapping("/{imo}")
    public ResponseEntity<Page<MeasurementResponseDTO>> findMeasures(@PathVariable("imo") String imo,
                                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                     @RequestParam(value = "size", defaultValue = "20") Integer size){
        return ResponseEntity.ok(measurementService.findByImo(imo, page, size));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMeasurement(@PathVariable("id") Long id){
        measurementService.deleteMeasurement(id);
        return ResponseEntity.ok().build();
    }
}
