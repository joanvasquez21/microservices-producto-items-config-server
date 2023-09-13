package com.springboot.app.item.controllers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.app.item.models.Item;
import com.springboot.app.commons.models.entity.Producto;
import com.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
public class ItemController {

	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private Environment env;

	@Autowired
	private CircuitBreakerFactory cbFactory;

	// @Qualifier("serviceFeign")
	@Autowired
	@Qualifier("serviceFeign")
	private ItemService itemService;

	@Value("${configuracion.texto}")
	private String texto;

	// Metodos handler

	@GetMapping("/listar")
	public List<Item> listar() {
		return itemService.findAll();
	}

	@GetMapping("/listar/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		// Creamos un nuevo cortocircuito llamado items
		return cbFactory.create("items").run(() -> itemService.findById(id, cantidad),
				e -> metodoAlternativo(id, cantidad, e));
	}

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping("/listar2/{id}/cantidad/{cantidad}")
	public Item detalle2(@PathVariable Long id, @PathVariable Integer cantidad) {
		// Creamos un nuevo cortocircuito llamado items
		return itemService.findById(id, cantidad);
	}

	// Tenemos que envolver esta llamada con CompletableFuture para ejecutarlo de
	// forma asincrona

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo2")
	@TimeLimiter(name = "items")
	@GetMapping("/listar3/{id}/cantidad/{cantidad}")
	public CompletableFuture<Item> detalle3(@PathVariable Long id, @PathVariable Integer cantidad) {
		// Creamos un nuevo cortocircuito llamado items
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, cantidad));
	}

	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {

		logger.info(e.getMessage());

		Item item = new Item();
		Producto producto = new Producto();

		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setName("Camara Sony");
		producto.setPrice(500.00);

		item.setProducto(producto);

		return item;

	}

	public CompletableFuture<Item> metodoAlternativo2(Long id, Integer cantidad, Throwable e) {

		logger.info(e.getMessage());

		Item item = new Item();
		Producto producto = new Producto();

		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setName("Camara Sony");
		producto.setPrice(500.00);

		item.setProducto(producto);

		return CompletableFuture.supplyAsync(() -> item);

	}

	@GetMapping("/obtener-config")
	public ResponseEntity<?> obtenerConfig() {
		Map<String, String> json = new HashMap<>();

		json.put("texto", texto);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.texto"));
		}
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/crear")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto crear(@RequestBody Producto producto) {
		return itemService.save(producto);
	}
	
	@PutMapping("/editar/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Producto editar(@RequestBody Producto producto, @PathVariable Long id) {
		return itemService.update(producto, id);
	}
	
	@DeleteMapping("/eliminar/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		itemService.delete(id);
	}
	
	

}
