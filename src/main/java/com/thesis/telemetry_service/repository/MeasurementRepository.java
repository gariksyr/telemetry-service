package com.thesis.telemetry_service.repository;

import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    Page<Measurement> findMeasurementByImo(Pageable page, String imo);
}
