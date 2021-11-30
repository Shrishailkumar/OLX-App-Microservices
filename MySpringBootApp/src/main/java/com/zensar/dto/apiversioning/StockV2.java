package com.zensar.dto.apiversioning;

public class StockV2 {
	private String name;
	private String market;

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StockV2(String name, String market) {
		this.name = name;
		this.market = market;
	}

	@Override
	public String toString() {
// TODO Auto-generated method stub
		return super.toString();
	}

}
