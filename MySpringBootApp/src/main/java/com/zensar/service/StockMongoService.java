package com.zensar.service;

import java.util.List;

import com.zensar.dto.Stock;

public interface StockMongoService {
	public Stock createNewStock(Stock stock);

	public boolean deleteAllStocks();

	public boolean deleteStockById(int stockId);

	public Stock updateStock(int stockId, Stock stock);

	public Stock getStockById(int stockId);

	public List<Stock> getAllStock();

	List<Stock> findByMarketName(String market);

	List<Stock> findByName(String name);

	List<Stock> findByNameAndMarketName(String name, String marketName);

	//List<Stock> findByNameLike(String name);

	List<Stock> findByOrderByName(String sortType);

	List<Stock> findByPage(int startIndex, int records);

}
