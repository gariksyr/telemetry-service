package com.thesis.telemetry_service.service;

import com.thesis.telemetry_service.dto.AlertResponseDTO;
import com.thesis.telemetry_service.dto.RestPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceClient {
    private final RestTemplate restTemplate;
    private final String ALERT_URL = "http://alert-service:8082/api/v1/alerts/vessel/";

    public List<AlertResponseDTO> getAlerts(String imo) {
        try {
            ResponseEntity<RestPageResponse<AlertResponseDTO>> response = restTemplate.exchange(
                    ALERT_URL + imo + "?page=0&size=100",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );

            if (response.getBody() != null) {
                return response.getBody().getContent();
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении алертов: " + e.getMessage());
        }
        return Collections.emptyList();
        }
    }
