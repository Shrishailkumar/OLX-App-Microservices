package com.zensar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zensar.dto.Stock;
import com.zensar.entity.StockDocument;
import com.zensar.entity.StockEntity;
import com.zensar.exception.InvalidStockIdException;
import com.zensar.repo.StockMongoRepo;
import com.zensar.repo.StockRepo;

@Service(value="MONGO_SERVICE")
public class StockServiceMongoImpl implements StockMongoService{

	@Autowired
	StockMongoRepo repo;

	@Autowired
	ModelMapper modelMapper;

	private StockDocument getStockDocumentFromDTO(Stock stock) {

		TypeMap<Stock, StockDocument> typeMap = this.modelMapper.typeMap(Stock.class, StockDocument.class);

		typeMap.addMappings(mapper -> {
			mapper.map(source -> source.getCost(), StockDocument::setPrice);
			mapper.map(source -> source.getMarket(), StockDocument::setMarketName);
		});

		StockDocument stockDocument = this.modelMapper.map(stock, StockDocument.class);

		return stockDocument;
	}

	private Stock getStockDTOFromDocument(StockDocument stockDocument) {

		TypeMap<StockDocument, Stock> typeMap = this.modelMapper.typeMap(StockDocument.class, Stock.class);
		typeMap.addMappings(mapper -> {
			mapper.map(source->source.getMarketName(), Stock::setMarket);
			mapper.map(source -> source.getPrice(), Stock::setCost);
		});
		Stock stock = this.modelMapper.map(stockDocument, Stock.class);
		return stock;
	}

	private List<Stock> getDTOListFromStockEntityList(List<StockDocument> stockDocuments) {
		List<Stock> stockDtoList = new ArrayList<Stock>();
		for (StockDocument stockEntity : stockDocuments) {
			stockDtoList.add(getStockDTOFromDocument(stockEntity));
		}
		return stockDtoList;

	}

	@Override
	public Stock createNewStock(Stock stock) {
		// TODO Auto-generated method stub
		StockDocument stockEntity = getStockDocumentFromDTO(stock);
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
		Optional<StockDocument> opStockEntity = repo.findById(stockId);
		if (opStockEntity.isPresent()) {
			StockDocument stockEntity = opStockEntity.get();
			/*
			 * stockEntity.setMarketName(stock.getMarketName());
			 * stockEntity.setName(stock.getName()); stockEntity.setPrice(stock.getPrice());
			 */
			stock.setId(stockId);
			stockEntity = getStockDocumentFromDTO(stock);
			stockEntity = repo.save(stockEntity);
			Stock stockDto = getStockDTOFromDocument(stockEntity);
			return stockDto;
		} else {
			throw new InvalidStockIdException("Stock is not present");
		}
	}

	@Override
	public Stock getStockById(int stockId) {
		Optional<StockDocument> sEntity = repo.findById(stockId);
		if (sEntity.isPresent()) {
			StockDocument stockEntity = sEntity.get();
			Stock stockDto = this.modelMapper.map(stockEntity, Stock.class);
			return stockDto;
		} else {
			throw new InvalidStockIdException("Stock is not present");
		}
	}

	@Override
	public List<Stock> getAllStock() {
		List<StockDocument> stockentityList = repo.findAll();
		return getDTOListFromStockEntityList(stockentityList);
	}

	@Override
	public List<Stock> findByMarketName(String market) {
		List<StockDocument> stockEntityList = repo.findByMarketName(market);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByName(String name) {
		List<StockDocument> stockEntityList = repo.findByName(name);
		return getDTOListFromStockEntityList(stockEntityList);
	}

	@Override
	public List<Stock> findByNameAndMarketName(String name, String marketName) {
		List<StockDocument> stockEntityList = repo.findByNameAndMarketName(name, marketName);
		return getDTOListFromStockEntityList(stockEntityList);
	}
	/*
	 * @Override public List<Stock> findByNameLike(String name) {
	 * List<StockDocument> stockEntityList = repo.findByNameLike(name); return
	 * getDTOListFromStockEntityList(stockEntityList); }
	 */

	@Override
	public List<Stock> findByOrderByName(String sortType) {
		List<StockDocument> stockEntityList = null;
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
		Page<StockDocument> stockEntityPage = repo.findAll(pageWithFewRecords);
		List<StockDocument> stockEntityList = stockEntityPage.getContent();
		return getDTOListFromStockEntityList(stockEntityList);
	}

	

}
