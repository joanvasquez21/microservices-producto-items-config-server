package com.springboot.app.productos.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.commons.models.entity.Producto;

public interface ProductoDAO extends CrudRepository<Producto, Long> {
	
	

}
