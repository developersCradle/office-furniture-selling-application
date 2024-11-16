package com.officesales.office_furniture_sales.service;

import java.math.BigDecimal;

import com.officesales.office_furniture_sales.dto.OrderDTO;

public interface OrderService {
	
	// START - Order basic operations.
	OrderDTO createOrderForCustomer(Long customerId);
	OrderDTO addNewProductToOrder(Long orderId, Long productId, int quantity);
	OrderDTO updateItemQuantity(Long orderId, Long itemId, int quantity);
	OrderDTO removeProductFromOrder(Long orderId, Long itemId);
	// END - Order basic operations.
	
	BigDecimal calculateTotalOrderPrice(Long orderId);
	
	// START - External services.
	boolean validateOrderAgainstExternalService(OrderDTO orderDTO);
	// END - External services.
}
