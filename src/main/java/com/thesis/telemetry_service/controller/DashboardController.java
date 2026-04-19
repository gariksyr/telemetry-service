package com.thesis.telemetry_service.controller;

import com.thesis.telemetry_service.model.Measurement;
import com.thesis.telemetry_service.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
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
public class DashboardController {
    private final MeasurementRepository measurementRepository;

    @GetMapping("/{imo}")
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

        model.addAttribute("history", historyDto);
        model.addAttribute("vesselImo", imo);

        return "vessel_stats";
    }
}
