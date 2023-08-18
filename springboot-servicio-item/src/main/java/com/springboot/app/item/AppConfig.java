package com.springboot.app.item;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//Registrar como un componente de spring

@Configuration
public class AppConfig {

	@Bean("clienteRest")
	@LoadBalanced
	public RestTemplate registrarRestTemplate() {
		return  new RestTemplate();
	}
}
