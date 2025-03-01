package com.grocerybookingapp.services.impl;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.grocerybookingapp.entities.Item;
import com.grocerybookingapp.exceptions.ResourceNotFoundException;
import com.grocerybookingapp.repositories.ItemRepository;
import com.grocerybookingapp.services.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Value("${grocery.low.stock.message}")
    private String lowStockMessage;

    @Value("${grocery.high.stock.message}")
    private String highStockMessage;
	

	@Autowired
	ItemRepository itemRepository;

	Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Override
	public Item addItem(Item item) {
		String message = (item.getInventory() < 10) ? lowStockMessage : highStockMessage;
		item.setMessage(message);
		String Id = UUID.randomUUID().toString();
		item.setItemId(Id);
		Date date = new Date();
		item.setAddedDate(date);
		item.setAddedDate(date);
		return itemRepository.save(item);
	}

	@Override
	public Item updateItem(Item item, String itemId) {
		Item item1 = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not found in Database !!"));
		item1.setPrice(item.getPrice());
		item1.setInventory(item.getInventory());
		item1.setStock(item.isStock());
		item1.setTitle(item.getTitle());
		return itemRepository.save(item1);
	}

	@Override
	public void deleteItem(String itemId) throws IOException {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not founf in Database !!"));
		itemRepository.delete(item);
	}

	@Override
	public Item getItemById(String itemId) {
		Item item = itemRepository.findById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Item not founf in Database !!"));
		return item;
	}

	@Override
	public Page<Item> getAllItem(int pageNumber, int pageSize, String sortBy, String sortDir) {
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Item> page = itemRepository.findAll(pageable);
		return page;
	}

	@Override
	public List<String> placeOrder(List<String> itemIds) {
		List<String> itemList = new ArrayList<>();
		double totalPrice = 0.0;
		for (String itemId : itemIds) {
			Optional<Item> item = itemRepository.findById(itemId);

			if (item.isPresent()) {
				itemList.add("Item: " + item.get().getTitle() + ", Price: " + item.get().getPrice());
				totalPrice += item.get().getPrice();
			} else {
				itemList.add("Item ID " + itemId + " not available");
			}
		}
		itemList.add("Total Price: " + totalPrice);
		return itemList;
	}

}
