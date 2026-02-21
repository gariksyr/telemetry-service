package com.thesis.telemetry_service.config;

import com.thesis.telemetry_service.dto.MeasurementRequestDTO;
import com.thesis.telemetry_service.dto.MeasurementResponseDTO;
import com.thesis.telemetry_service.model.Measurement;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        // 1. ПУТЬ ТУДА: DTO -> Entity (Point)
        Converter<MeasurementRequestDTO, Point> toPoint = context -> {
            MeasurementRequestDTO src = context.getSource();
            if (src == null || src.getLongitude() == null || src.getLatitude() == null) return null;
            return geometryFactory.createPoint(new Coordinate(src.getLongitude(), src.getLatitude()));
        };

        mapper.typeMap(MeasurementRequestDTO.class, Measurement.class)
                .addMappings(m -> m.using(toPoint).map(src -> src, Measurement::setLocation));

        // 2. ПУТЬ ОБРАТНО: Entity (Point) -> DTO
        // Создаем маппинг для Response DTO
        mapper.typeMap(Measurement.class, MeasurementResponseDTO.class).addMappings(m -> {
            m.map(src -> src.getLocation().getY(), MeasurementResponseDTO::setLatitude);
            m.map(src -> src.getLocation().getX(), MeasurementResponseDTO::setLongitude);
        });

        return mapper;
    }
}
