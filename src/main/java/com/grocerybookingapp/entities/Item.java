package com.grocerybookingapp.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Item")
public class Item {
	
	@Id
	@Schema(name = "ItemID",description = "Items ID !!")
	private String itemId;
	
	@Schema(name = "Title",description = "Title of item !!")
	private String title;

	@Schema(name = "Price",description = "Price of items")
	private int price;
	
	@Schema(name = "Inventory",description = "Inventory of item")
	private int inventory;

	@Schema(name = "AddedDate",description = "Item added date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date addedDate;

	@Schema(name = "Stock",description = "Stock available or not")
	private boolean stock;
	
	@Schema(name = "Message",description = "Message to display bellow Item ")
	String message ;
	
}
