package com.springboot.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringbootServicioGatewayServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioGatewayServeApplication.class, args);
	}

}
