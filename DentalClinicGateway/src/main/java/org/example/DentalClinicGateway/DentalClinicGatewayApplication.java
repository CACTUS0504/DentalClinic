package org.example.DentalClinicGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DentalClinicGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentalClinicGatewayApplication.class, args);
	}

}
