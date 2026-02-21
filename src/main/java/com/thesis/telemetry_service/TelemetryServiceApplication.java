package com.thesis.telemetry_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TelemetryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelemetryServiceApplication.class, args);
	}

}
