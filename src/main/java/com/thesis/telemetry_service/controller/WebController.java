package com.thesis.telemetry_service.controller;

import com.thesis.telemetry_service.dto.AlertResponseDTO;
import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.model.Measurement;
import com.thesis.telemetry_service.repository.MeasurementRepository;
import com.thesis.telemetry_service.service.AlertServiceClient;
import com.thesis.telemetry_service.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class WebController {
    private final MeasurementRepository measurementRepository;
    private final MeasurementService measurementService;
    private final AlertServiceClient alertServiceClient;

    @GetMapping("/vessel/{imo}")
    public String getVesselDashboard(@PathVariable String imo, Model model) {
        List<Measurement> entities = measurementRepository.findTop50ByVesselImoOrderByTimestampDesc(imo);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");

        List<Map<String, Object>> historyDto = entities.stream().map(m -> {
            Map<String, Object> map = new HashMap<>();
            map.put("timestampFormatted", m.getTimestamp().format(formatter));
            map.put("temperature", m.getTemperature());
            map.put("humidity", m.getHumidity());
            map.put("pressure", m.getPressure());
            map.put("speed", m.getSpeed());
            map.put("windSpeed", m.getWindSpeed());
            map.put("windDirection", m.getWindDirection());
            map.put("latitude", m.getLatitude());
            map.put("longitude", m.getLongitude());
            return map;
        }).toList();
        List<AlertResponseDTO> alerts = alertServiceClient.getAlerts(imo);

        model.addAttribute("history", historyDto);
        model.addAttribute("alerts", alerts);
        model.addAttribute("vesselImo", imo);

        return "vessel_stats";
    }
    @GetMapping()
    public String index(Model model) {
        Page<MeasurementResponseDTO> distinctImos = measurementService.getLatestPositions(0, 20);
        model.addAttribute("distinctImos", distinctImos);
        return "index";
    }
}
