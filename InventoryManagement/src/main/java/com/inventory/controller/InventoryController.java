package com.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.dto.ProductIdList;
import com.inventory.dto.ProductRequest;
import com.inventory.dto.ProductResponse;
import com.inventory.dto.ProductResponseList;
import com.inventory.dto.StockRequest;
import com.inventory.dto.StockResponse;
import com.inventory.dto.StockUpdate;
import com.inventory.dto.StockUpdateResponse;
import com.inventory.service.InventoryService;

@RestController
@RequestMapping(value = "/inventory")
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@PostMapping("/product")
	private ProductResponse addProduct(@RequestBody ProductRequest product) {

		return inventoryService.addProduct(product);
	}

	@PostMapping("/product/list")
	private List<ProductResponse> addProductList(@RequestBody List<ProductRequest> product) {

		return inventoryService.addProductList(product);
	}

	@PostMapping("/stock")
	private StockResponse addStock(@RequestBody StockRequest stock) {

		return inventoryService.addStock(stock);
	}

	@GetMapping("/product/{id}")
	private ProductResponse getProduct(@PathVariable("id") String productId) {
		return inventoryService.getProduct(productId);
	}

	@GetMapping("/product/idList")
	private ProductResponseList getProductByIds(@RequestParam("ids") Long[] ids) {

		return inventoryService.getProductByIds(ids);
	}

	@GetMapping("/product/list")
	private List<ProductResponse> getProductList() {
		return inventoryService.getProductList();
	}

	@PostMapping("/stock/list")
	private StockUpdateResponse addStock(@RequestBody StockUpdate stockUpdate) {

		return inventoryService.updateStockList(stockUpdate);
	}
}
