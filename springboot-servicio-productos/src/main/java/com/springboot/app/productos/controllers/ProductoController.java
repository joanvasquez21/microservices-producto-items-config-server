package com.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.app.commons.models.entity.Producto;
import com.springboot.app.productos.models.service.IProductoService;

@RestController
public class ProductoController {

	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private IProductoService productoService;

	
	@GetMapping("/listar")
	public List<Producto> listar(){
		return productoService.findAll().stream().map(  producto -> {
			producto.setPort( Integer.parseInt(env.getProperty("local.server.port")));
			//producto.setPort(port);
			return producto;
			//Convertimos a  tipo list
		}).collect(Collectors.toList()) ;
	}
	// Estamos en el servicio PRODUCTOS -> ProductoController
	@GetMapping("/listar/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		if(id.equals(7L)) {
			//hacemos una pausa
			TimeUnit.SECONDS.sleep(5L);
		}
		
		Producto producto = productoService.findById(id);
		producto.setPort( Integer.parseInt(env.getProperty("local.server.port")));
		//producto.setPort(port);

		return producto;
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return productoService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		Producto productoDb = productoService.findById(id);
		
		productoDb.setName(producto.getName());
		productoDb.setPrice(producto.getPrice());
		productoDb.setCreateAt(producto.getCreateAt());
		
		return productoService.save(productoDb);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productoService.deleteById(id);
	}
	
}
