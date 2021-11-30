package com.zensar.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Stocks")
public class StockEntity {
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

	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "stock_name")
	private String name;

	@Column(name = "stock_price")
	private double price;

	@Column(name = "stock_market")
	private String marketName;

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public StockEntity(int id, String name, String marketName,double price) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.marketName = marketName;
	}
	
	public StockEntity(String name, String marketName,double price) {
		super();
		this.name = name;
		this.price = price;
		this.marketName = marketName;
	}

	public StockEntity() {

	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "StockEntity [id=" + id + ", name=" + name + ", marketName=" + marketName + ", price=" + price + "]";
	}

}
