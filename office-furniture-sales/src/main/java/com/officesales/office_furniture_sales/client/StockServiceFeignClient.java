package com.officesales.office_furniture_sales.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.officesales.office_furniture_sales.dto.OrderDTO;

/*
 * Calls the stock service to validate whether the Products in 
*  the provided order are available in stock or are valid. 
 */

@FeignClient(name = "stock-service", url = "http://some-random-stock-service/api")
public interface StockServiceFeignClient {
	
	// Random things, something like this.
    @PostMapping("/stock/check")
    boolean isOrderValid(@RequestBody OrderDTO orderDTO);
}