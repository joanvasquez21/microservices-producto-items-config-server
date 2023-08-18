package com.springboot.app.productos.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.productos.models.dao.ProductoDAO;
import com.springboot.app.productos.models.entity.Producto;


@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoDAO productoDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDAO.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

}
