package com.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.springboot.app.item.models.Item;
import com.springboot.app.commons.models.entity.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {

	@Autowired
	private RestTemplate clienteRest;
	
	@Override
	public List<Item> findAll() {
		//Utilizando Ribbon para balanceo de carga en Feign
		//List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://localhost:8001/listar", Producto[].class));
		//Utilizando Ribbon para balanceo de carga en  RestTemplate
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		//Transformamos esta lista de items a lista de productos
		return productos.stream().map( p ->  new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {

		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());		
		//Utilizando Ribbon para balanceo de carga en Feign
		//Producto producto =  clienteRest.getForObject("http://localhost:8001/listar/{id}", Producto.class,pathVariable);
		Producto producto =  clienteRest.getForObject("http://servicio-productos/listar/{id}", Producto.class,pathVariable);

		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {

		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		
		
		ResponseEntity<Producto> response = clienteRest.exchange( "http://servicio-productos/crear", HttpMethod.POST, body, Producto.class);
		Producto productoResponse = response.getBody();
		
		return productoResponse;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		ResponseEntity<Producto> response = clienteRest.exchange("http://servicio-productos/editar/{id}", 
														HttpMethod.PUT, body, Producto.class, pathVariable);
		
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		
		clienteRest.delete("http://servicio-productos/eliminar/{id}", HttpMethod.DELETE, pathVariable );
	}

}
