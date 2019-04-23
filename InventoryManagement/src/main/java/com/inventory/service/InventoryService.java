package com.inventory.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventory.dto.OrderLine;
import com.inventory.dto.ProductIdList;
import com.inventory.dto.ProductRequest;
import com.inventory.dto.ProductResponse;
import com.inventory.dto.ProductResponseList;
import com.inventory.dto.StockRequest;
import com.inventory.dto.StockResponse;
import com.inventory.dto.StockUpdate;
import com.inventory.dto.StockUpdateResponse;
import com.inventory.entity.Product;
import com.inventory.entity.Stock;
import com.inventory.exception.InvalidStockQuantityException;
import com.inventory.exception.NonExistentProductException;
import com.inventory.exception.StockInSufficientException;
import com.inventory.repository.ProductRepository;
import com.inventory.repository.StockRepository;

@Service
public class InventoryService {

	@Autowired
	public ProductRepository repo;

	@Autowired
	public StockRepository repoStock;

	public ProductResponse addProduct(ProductRequest prodReq) {

		Product prod = new Product();
		prod.setProductName(prodReq.getProductName());
		prod.setProductDesc(prodReq.getProductDesc());
		prod.setPrice(prodReq.getPrice());
		Stock stock = new Stock();
		stock.setProduct(prod);
		stock.setQuantity(0);
		prod.setStock(stock);
		Product savedEntity = repo.save(prod);

		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(savedEntity.getId());
		productResponse.setProductName(savedEntity.getProductName());
		productResponse.setProductDesc(savedEntity.getProductDesc());
		productResponse.setPrice(savedEntity.getPrice());
		return productResponse;

	}

	@Transactional
	public StockResponse addStock(StockRequest stock) {
		Optional<Stock> existingStockOp = repoStock.findById(stock.getProductId());

		StockResponse stockRep = new StockResponse();
		if (existingStockOp.isPresent()) {
			Stock existingStock = existingStockOp.get();
			if ((existingStock.getQuantity() + stock.getQuantity()) < 0)
				throw new InvalidStockQuantityException("Stock quantity cannot be less than zero");
			existingStock.setQuantity(existingStock.getQuantity() + stock.getQuantity());
			Stock existingStockUpdated = repoStock.save(existingStock);
			stockRep.setProductId(existingStockUpdated.getId());
			stockRep.setQuantity(existingStockUpdated.getQuantity());

		} else {
			Stock newStock = new Stock();
			if (stock.getQuantity() < 0)
				throw new InvalidStockQuantityException("Stock quantity cannot be less than zero");
			newStock.setQuantity(stock.getQuantity());
			Optional<Product> prodOp = repo.findById(stock.getProductId());
			if (!prodOp.isPresent())
				throw new NonExistentProductException("Product not available in inventory,cannot add stock");
			newStock.setProduct(prodOp.get());
			Stock newStockUpated = repoStock.save(newStock);
			stockRep.setProductId(newStockUpated.getId());
			stockRep.setQuantity(newStockUpated.getQuantity());
		}
		return stockRep;

	}

	public ProductResponse getProduct(String productId) {

		Optional<Product> productOpt = repo.findById(Long.valueOf(productId));
		Product prod = productOpt.get();

		ProductResponse productResponse = new ProductResponse();
		productResponse.setProductId(prod.getId());
		productResponse.setProductName(prod.getProductName());
		productResponse.setProductDesc(prod.getProductDesc());
		productResponse.setPrice(prod.getPrice());
		return productResponse;
	}

	@Transactional
	public StockUpdateResponse updateStockList(StockUpdate stock) {

		List<Long> ids = new ArrayList<>();
		List<OrderLine> receivedStockList = stock.getStockList();

		for (OrderLine product : receivedStockList) {
			ids.add(product.getProductId());
		}

		List<Stock> existingStockList = repoStock.findAllById(ids);
		if (existingStockList.size() != receivedStockList.size())
			throw new NonExistentProductException("Product is not available");

		Comparator<Stock> byId = (Stock o1, Stock o2) -> o1.getId().compareTo(o2.getId());
		Comparator<OrderLine> byProductPd = (OrderLine o1, OrderLine o2) -> o1.getProductId()
				.compareTo(o2.getProductId());
		Collections.sort(receivedStockList, byProductPd);
		Collections.sort(existingStockList, byId);

		List<OrderLine> stockNotsufficientList = new ArrayList<>();
		List<OrderLine> stockSufficientList = new ArrayList<>();
		for (int i = 0; i < existingStockList.size(); i++) {
			if ((existingStockList.get(i).getQuantity() + receivedStockList.get(i).getQuantity()) >= 0) {
				existingStockList.get(i)
						.setQuantity(existingStockList.get(i).getQuantity() + receivedStockList.get(i).getQuantity());
				stockSufficientList
						.add(new OrderLine(existingStockList.get(i).getId(), existingStockList.get(i).getQuantity()));
			} else {
				stockNotsufficientList
						.add(new OrderLine(existingStockList.get(i).getId(), existingStockList.get(i).getQuantity()));
			}
		}

		List<Stock> updatedStockList;
		if (stockNotsufficientList.size() == 0) {
			updatedStockList = repoStock.saveAll(existingStockList);
		} else
			throw new StockInSufficientException(stockNotsufficientList);

		StockUpdateResponse stockRep = new StockUpdateResponse();
		stockRep.setStockList(stockSufficientList);
		return stockRep;

	}

	public List<ProductResponse> addProductList(List<ProductRequest> prodReqList) {

		List<Product> prodList = new ArrayList<>();
		for (ProductRequest prodReq : prodReqList) {
			Product prod = new Product();
			prod.setProductName(prodReq.getProductName());
			prod.setProductDesc(prodReq.getProductDesc());
			prod.setPrice(prodReq.getPrice());
			Stock stock = new Stock();
			stock.setProduct(prod);
			stock.setQuantity(0);
			prod.setStock(stock);
			prodList.add(prod);
		}
		List<Product> savedEntity = repo.saveAll(prodList);

		List<ProductResponse> prodResponseList = new ArrayList<>();
		for (Product prod : savedEntity) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(prod.getId());
			productResponse.setProductName(prod.getProductName());
			productResponse.setProductDesc(prod.getProductDesc());
			productResponse.setPrice(prod.getPrice());
			prodResponseList.add(productResponse);
		}
		return prodResponseList;
	}

	public List<ProductResponse> getProductList() {
		List<Product> productList = repo.findAll();

		List<ProductResponse> prodResponseList = new ArrayList<>();
		for (Product prod : productList) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(prod.getId());
			productResponse.setProductName(prod.getProductName());
			productResponse.setProductDesc(prod.getProductDesc());
			productResponse.setPrice(prod.getPrice());
			prodResponseList.add(productResponse);
		}
		return prodResponseList;
	}

	public ProductResponseList getProductByIds(Long[] ids) {

		List<Long> list = Arrays.asList(ids);
		List<Product> productList = repo.findAllById(list);
		List<ProductResponse> prodResponseList = new ArrayList<>();
		for (Product prod : productList) {
			ProductResponse productResponse = new ProductResponse();
			productResponse.setProductId(prod.getId());
			productResponse.setProductName(prod.getProductName());
			productResponse.setProductDesc(prod.getProductDesc());
			productResponse.setPrice(prod.getPrice());
			prodResponseList.add(productResponse);
		}
		ProductResponseList productResponseList = new ProductResponseList();
		productResponseList.setProduct(prodResponseList);
		return productResponseList;
	}

}
