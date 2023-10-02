package com.example.DentalClinicServerDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DentalClinicServerDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentalClinicServerDiscoveryApplication.class, args);
	}

}
