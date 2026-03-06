package com.thesis.telemetry_service.repository;

import com.thesis.telemetry_service.model.Measurement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
    Page<Measurement> findMeasurementByVesselImo(Pageable page, String imo);
    Optional<Measurement> findMeasurementById(Long id);
    //TODO проверить что Postgis стоит в контейнере докера и дополнить запрос тем что нейронка предложила
    @Query(value = """
        SELECT * FROM measurement 
        WHERE vessel_imo = :imo 
        AND ST_DWithin(location, ST_SetSRID(ST_Point(:lon, :lat), 4326), :radius)
        """, nativeQuery = true)
    Page<Measurement> findNearPoint(Pageable page, String imo, Double lat, Double lon, Double radius);
}