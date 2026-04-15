package com.thesis.telemetry_service.repository;

import com.thesis.telemetry_service.model.Imo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImoRepository extends JpaRepository<Imo, Long> {
    Boolean existsByImo(String imo);
    void deleteByImo(String imo);
}
