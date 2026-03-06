package com.thesis.telemetry_service.service;


import com.thesis.telemetry_service.dto.MeasurementRequestDTO;
import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.exception.EntityNotFoundException;
import com.thesis.telemetry_service.model.Measurement;
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
    @Transactional
    public MeasurementResponseDTO addMeasurement(MeasurementRequestDTO measurementRequestDTO) {
        Boolean exists = stringRedisTemplate.hasKey("vessel:" + measurementRequestDTO.getVesselImo());
        if(Boolean.FALSE.equals(exists)){
            throw new EntityNotFoundException();
        }
        Measurement measurement = modelMapper.map(measurementRequestDTO, Measurement.class);
        measurementRepository.save(measurement);
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
}
