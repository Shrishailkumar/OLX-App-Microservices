package com.zensar.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Stock Model")
public class Stock {
	@ApiModelProperty("unidue id for the stock")
	private int id;
	
	@ApiModelProperty("name of stock")
	private String name;
	
	@ApiModelProperty("price of the stock")
	private double cost;
	
	@ApiModelProperty("market name of the stock")
	private String market;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	
	public String getMarket() {
		return market;
	}

	public void setMarket(String marketName) {
		this.market = marketName;
	}

	public Stock(int id, String name, String market, double cost) {
		super();
		this.id = id;
		this.cost = cost;
		this.market = market;
		this.name = name;
	}
	public Stock() {
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "StockEntity [id=" + id + ", name=" + name + ", marketName=" + market + ", price=" + cost + "]";
	}

}
