package com.clinicapp.clinicapp_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ClinicappApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicappApiApplication.class, args);
		System.out.println("==========================================");
		System.out.println("  🏥 CLINICAPP - BACKEND INICIADO");
		System.out.println("  📍 Puerto: 8080");
		System.out.println("  📍 Context Path: /api");
		System.out.println("==========================================");
	}

}
