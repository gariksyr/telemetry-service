package com.thesis.telemetry_service.service;

import com.thesis.telemetry_service.model.Imo;
import com.thesis.telemetry_service.repository.ImoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImoService {
    private final ImoRepository imoRepository;
    private final StringRedisTemplate redisTemplate;
    @Transactional
    public void deleteFromEverywhereByImo(String imo) {
        imoRepository.deleteByImo(imo);
        String cacheKey = "vessel:" + imo;
        redisTemplate.delete(cacheKey);
    }
    @Transactional
    public void saveEverywhereByImo(String imoNum) {
        saveInRedisImo(imoNum);
        Imo imo = new Imo();
        imo.setImo(imoNum);
        imoRepository.save(imo);
    }
    @Transactional
    public void saveInRedisImo(String imo) {
        redisTemplate.opsForValue().set(
                "vessel:" + imo,
                "REGISTERED",
                Duration.ofHours(24)
        );
    }
}
