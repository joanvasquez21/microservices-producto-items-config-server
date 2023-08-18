package com.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.springboot.app.item.clientes.ProductoClienteRest;
import com.springboot.app.item.models.Item;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductoClienteRest clientFeign;
	
	@Override
	public List<Item> findAll() {
		return   clientFeign.listar().stream().map( p ->  new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		// TODO Auto-generated method stub
		return new Item(clientFeign.detalle(id), cantidad);
	}

	
	
}
