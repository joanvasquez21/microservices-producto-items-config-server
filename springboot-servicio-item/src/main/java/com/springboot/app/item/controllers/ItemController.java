package com.springboot.app.item.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Producto;
import com.springboot.app.item.models.service.ItemService;

@RestController
public class ItemController {

	//@Qualifier("serviceFeign")
	@Autowired 
	@Qualifier("serviceFeign")
	private ItemService itemService;
	
	//Metodos handler
	
	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name="nombre") String nombre, @RequestHeader(name="token-request") String token ){
		System.out.println(nombre);
		System.out.println(token);
		return itemService.findAll();
	}
	
	//En caso falla, llama a  un metodo alternativo (fallbackmethod)
	@HystrixCommand(fallbackMethod="metodoAlternativo")
	@GetMapping("listar/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id,@PathVariable Integer cantidad ) {
		return itemService.findById(id, cantidad);
	}
	
	public  Item metodoAlternativo(Long id, Integer cantidad) {
		Item item = new Item();
		Producto producto = new Producto();
		
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setName("Camara Sony");
		producto.setPrice(500.00);
		
		item.setProducto(producto);
		
		return item;
		
	}
	
}
