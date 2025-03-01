package com.grocerybookingapp.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.grocerybookingapp.entities.Item;
import com.grocerybookingapp.other.PageableResponse;

public interface ItemService {
	//Create
	Item addItem(Item item);

	//update
	Item updateItem(Item item,String itemId);

	//delete
	void deleteItem(String itemId) throws IOException;

	//Get single
	Item getItemById(String itemId);
	
	List<String> placeOrder(List<String> itemIds);

	//getAll
	Page<Item> getAllItem(int pageNumber,int pageSize,String sortBy,String sortDir);
}
