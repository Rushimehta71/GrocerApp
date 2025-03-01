package com.grocerybookingapp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grocerybookingapp.entities.Item;
import com.grocerybookingapp.other.ApiResponseMessage;
import com.grocerybookingapp.services.ItemService;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.*;



@RestController
@RequestMapping("/item")
@Tag(name = "Item Controller", description = "These are Items APIs")
public class ItemController {

	@Autowired
	ItemService itemService;

	Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Operation(summary = "Add new iteam")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = "New Item added !!") })
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping()
	public ResponseEntity<Item> addItem(@RequestBody Item item) {
		Item itemCreated = itemService.addItem(item);
		return new ResponseEntity<Item>(itemCreated, HttpStatus.CREATED);
	}

	@Operation(summary = "update exsiting iteam")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = "Item updated !!") })
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{itemId}")
	public ResponseEntity<Item> updateItem(@RequestBody Item item, @PathVariable String itemId) {
		Item itemUpdated = itemService.updateItem(item, itemId);
		return new ResponseEntity<Item>(itemUpdated, HttpStatus.OK);
	}

	@Operation(summary = "Delete iteam by user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = "Item Deleted !!") })
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{itemId}")
	public ResponseEntity<ApiResponseMessage> deleteItem(@PathVariable String itemId) throws IOException {
		itemService.deleteItem(itemId);
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Item Got Deleted").success(true)
				.status(HttpStatus.OK).build();
		return new ResponseEntity<ApiResponseMessage>(apiResponseMessage, HttpStatus.OK);
	}

	@Operation(summary = "Get iteam by user id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = " Get Item details !!") })
	@GetMapping("/{itemId}")
	public ResponseEntity<Item> getItem(@PathVariable String itemId) {
		Item item = itemService.getItemById(itemId);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}

	@Operation(summary = "Get all iteams")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = " Get all Items details  !!") })
	@GetMapping()
	public ResponseEntity<Page<Item>> getAllItem(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumeber,
			@RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
		Page<Item> itemlist = itemService.getAllItem(pageNumeber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(itemlist, HttpStatus.OK);
	}

	@Operation(summary = "Place Order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Success | OK"),
			@ApiResponse(responseCode = "401", description = "Not authorized !!"),
			@ApiResponse(responseCode = "201", description = "Congratulations Order placed !!") })
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/order")
	public ResponseEntity<List<String>> placeOrder(@RequestBody List<String> itemIds) {
		List<String> itemlist = itemService.placeOrder(itemIds);
		return new ResponseEntity<>(itemlist, HttpStatus.OK);
	}
}
