package com.thesis.telemetry_service.repository;

import com.thesis.telemetry_service.model.Measurement;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    Page<Measurement> findMeasurementByVesselImo(Pageable page, String imo);
    List<Measurement> findTop50ByVesselImoOrderByTimestampDesc(@NotBlank String vesselImo);
    Optional<Measurement> findMeasurementById(Long id);
    @Query(value = """
        SELECT * FROM measurement 
        WHERE vessel_imo = :imo 
        AND ST_DWithin(location::geography, ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography, :radius)
        """, nativeQuery = true)
    Page<Measurement> findNearPoint(Pageable page, String imo, Double lat, Double lon, Double radius);
    @Query(value = """
    SELECT DISTINCT ON (vessel_imo) * FROM measurement 
    ORDER BY vessel_imo, timestamp DESC
    """, nativeQuery = true)
    Page<Measurement> findLatestMeasurements(Pageable page);
}