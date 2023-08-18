  package com.springboot.app.item.models.service;

import java.util.List;

import com.springboot.app.item.models.Item;

public interface ItemService {

	public List<Item> findAll();
	
	public Item findById(Long id, Integer cantidad);
}
