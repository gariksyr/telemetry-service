package com.thesis.telemetry_service.service;


import com.thesis.telemetry_service.dto.MeasurementRequestDTO;
import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.exception.EntityNotFoundException;
import com.thesis.telemetry_service.kafka.TelemetryProducer;
import com.thesis.telemetry_service.model.Measurement;
import com.thesis.telemetry_service.repository.ImoRepository;
import com.thesis.telemetry_service.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final ModelMapper modelMapper;
    private final StringRedisTemplate stringRedisTemplate;
    private final TelemetryProducer telemetryProducer;
    private final ImoRepository imoRepository;
    private final ImoService  imoService;
    @Transactional
    public MeasurementResponseDTO addMeasurement(MeasurementRequestDTO measurementRequestDTO) {
        String imo = measurementRequestDTO.getVesselImo();
        Boolean exists = stringRedisTemplate.hasKey("vessel:" + imo);
        if(Boolean.FALSE.equals(exists)){
            exists = imoRepository.existsByImo(imo);
            if (Boolean.FALSE.equals(exists)) {
                throw new EntityNotFoundException();
            }
            else {
                imoService.saveInRedisImo(imo);
            }
        }
        Measurement measurement = modelMapper.map(measurementRequestDTO, Measurement.class);
        measurementRepository.save(measurement);
        telemetryProducer.sendTelemetry(measurement);
        return modelMapper.map(measurement, MeasurementResponseDTO.class);
    }
    public Page<MeasurementResponseDTO> findAll(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findAll(pageable).map(src -> modelMapper.map(src, MeasurementResponseDTO.class));
    }
    public Page<MeasurementResponseDTO> findByImo(String imo, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findMeasurementByVesselImo(pageable, imo).map(src -> modelMapper.map(src, MeasurementResponseDTO.class));
    }
    @Transactional
    public void deleteMeasurement(Long id){
        Measurement measurement = measurementRepository.findMeasurementById(id).orElseThrow(EntityNotFoundException::new);
        measurementRepository.delete(measurement);
    }
    public Page<MeasurementResponseDTO> findNear(int page, int size, String imo, Double lat, Double lon, Double radiusInMeters) {
        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findNearPoint(pageable, imo, lat, lon, radiusInMeters)
                .map(m -> modelMapper.map(m, MeasurementResponseDTO.class));
    }
    public Page<MeasurementResponseDTO> getLatestPositions(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return measurementRepository.findLatestMeasurements(pageable).map(m -> modelMapper.map(m, MeasurementResponseDTO.class));
    }
}
