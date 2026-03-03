package com.thesis.telemetry_service.repository;

import com.thesis.telemetry_service.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    Page<Measurement> findMeasurementByVesselImo(Pageable page, String imo);
    Optional<Measurement> findMeasurementById(Long id);
}
