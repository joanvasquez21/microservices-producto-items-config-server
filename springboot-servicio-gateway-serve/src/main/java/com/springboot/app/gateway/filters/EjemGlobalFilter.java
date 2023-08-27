package com.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class EjemGlobalFilter implements GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(EjemGlobalFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// con  el objeto exchange accedemos al request y response 
		logger.info("Ejecutando filtro pre");
		//Modificamos el REQUEST, siempre el REQUEST se modifica en el PRE, antes de que se envie la solicitud o request al servicio
		exchange.getRequest().mutate().headers( h -> h.add("token", "123456")); 
		
		//MonofromRunnable nos permite crear un objeto reactivo para implementar la tarea
		return chain.filter(exchange).then(Mono.fromRunnable( () -> {
			//Logica
			logger.info("ejecutando filtro post");
			//Aqui obtenemos en la respuesta el token
			 Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent( valor -> {
				exchange.getResponse().getHeaders().add("token", valor); 
			 });
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "rojo").build());
			//Modificamos el tipo de contenido
			//exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
			
			
		})) ;
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	
	
}
