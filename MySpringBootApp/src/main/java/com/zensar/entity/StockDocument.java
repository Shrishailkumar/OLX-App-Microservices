package com.zensar.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="stocks")
public class StockDocument {

	@org.springframework.data.annotation.Id
	private int id;

	private String name;

	public StockDocument(int id, String name, double price, String marketName) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.marketName = marketName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public StockDocument(String name, double price, String marketName) {
		super();
		this.name = name;
		this.price = price;
		this.marketName = marketName;
	}
	public StockDocument() {
		
	}
	@Override
	public String toString() {
		
		return super.toString();
	}

	private double price;

	private String marketName;


}
