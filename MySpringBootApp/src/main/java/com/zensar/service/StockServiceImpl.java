package com.zensar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.ModelMapper;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zensar.dto.Stock;
import com.zensar.entity.StockEntity;
import com.zensar.exception.InvalidStockIdException;
import com.zensar.repo.StockRepo;


public class StockServiceImpl implements StockService {
	@Autowired
	StockRepo repo;

	@Autowired
	ModelMapper modelMapper;

	private StockEntity getStockEntityFromDTO(Stock stock) {

		TypeMap<Stock, StockEntity> typeMap = this.modelMapper.typeMap(Stock.class, StockEntity.class);

		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCost(), StockEntity::setPrice);
			mapper.map(source -> source.getMarket(), StockEntity::setMarketName);
		});

		StockEntity stockEntity = this.modelMapper.map(stock, StockEntity.class);

		return stockEntity;
	}

	private Stock getStockDTOFromEntity(StockEntity stockEntity) {

		TypeMap<StockEntity, Stock> typeMap = this.modelMapper.typeMap(StockEntity.class, Stock.class);
		typeMap.addMappings(mapper -> {
			mapper.map(source->source.getMarketName(), Stock::setMarket);
			mapper.map(source -> source.getPrice(), Stock::setCost);
		});
		Stock stock = this.modelMapper.map(stockEntity, Stock.class);
		return stock;
	}

	private List<Stock> getDTOListFromStockEntityList(List<StockEntity> stockentityList) {
		List<Stock> stockDtoList = new ArrayList<Stock>();
		for (StockEntity stockEntity : stockentityList) {
			stockDtoList.add(getStockDTOFromEntity(stockEntity));
		}
		return stockDtoList;

	}

	@Override
	public Stock createNewStock(Stock stock) {
		// TODO Auto-generated method stub
		StockEntity stockEntity = getStockEntityFromDTO(stock);
		stockEntity = repo.save(stockEntity);
		return null;
	}

	@Override
	public boolean deleteAllStocks() {
		repo.deleteAll();
		return true;
	}

	@Override
	public boolean deleteStockById(int stockId) {
		repo.deleteById(stockId);
		return true;
	}

	@Override
	public Stock updateStock(int stockId, Stock stock) {
		Optional<StockEntity> opStockEntity = repo.findById(stockId);
		if (opStockEntity.isPresent()) {
			StockEntity stockEntity = opStockEntity.get();
			/*
			 * stockEntity.setMarketName(stock.getMarketName());
			 * stockEntity.setName(stock.getName()); stockEntity.setPrice(stock.getPrice());
			 */
			stock.setId(stockId);
			stockEntity = getStockEntityFromDTO(stock);
			stockEntity = repo.save(stockEntity);
			Stock stockDto = getStockDTOFromEntity(stockEntity);
			return stockDto;
		} else {
			throw new InvalidStockIdException("Stock is not present");
		}
	}

	@Override
	public Stock getStockById(int stockId) {
		Optional<StockEntity> sEntity = repo.findById(stockId);
		if (sEntity.isPresent()) {
			StockEntity stockEntity = sEntity.get();
			Stock stockDto = this.modelMapper.map(stockEntity, Stock.class);
			return stockDto;
		} else {
			throw new InvalidStockIdException("Stock is not present");
		}
	}

	@Override
	public List<Stock> getAllStock() {
		List<StockEntity> stockentityList = repo.findAll();
		return getDTOListFromStockEntityList(stockentityList);
	}

	@Override
	public List<Stock> findByMarketName(String market) {
		List<StockEntity> stockEntityList = repo.findByMarketName(market);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByName(String name) {
		List<StockEntity> stockEntityList = repo.findByName(name);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByNameAndMarketName(String name, String marketName) {
		List<StockEntity> stockEntityList = repo.findByNameAndMarketName(name, marketName);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByNameLike(String name) {
		List<StockEntity> stockEntityList = repo.findByNameLike(name);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByOrderByName(String sortType) {
		List<StockEntity> stockEntityList = null;
		if ("ASC".equalsIgnoreCase(sortType)) {
			stockEntityList = repo.findByOrderByNameAsc();
			/*
			 * List<Sort.Order> sortOrders = new ArrayList<Sort.Order>(); Sort.Order order_1
			 * = new Sort.Order(Sort.Direction.ASC, "name"); Sort.Order order_2 = new
			 * Sort.Order(Sort.Direction.DESC, "marketName"); sortOrders.add(order_1);
			 * sortOrders.add(order_2); stockEntityList =
			 * stockRepository.findAll(Sort.by(sortOrders)); stockEntityList =
			 * stockRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC,
			 * "price")));
			 */

		}
		if ("DESC".equalsIgnoreCase(sortType)) {
			stockEntityList = repo.findByOrderByNameDesc();
		}
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByPage(int startIndex, int records) {
		Pageable pageWithFewRecords = PageRequest.of(startIndex, records);
		Page<StockEntity> stockEntityPage = repo.findAll(pageWithFewRecords);
		List<StockEntity> stockEntityList = stockEntityPage.getContent();
		return getDTOListFromStockEntityList(stockEntityList);
	}

}
