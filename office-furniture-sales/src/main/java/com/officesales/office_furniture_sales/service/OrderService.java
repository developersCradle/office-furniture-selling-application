package com.officesales.office_furniture_sales.service;



import com.officesales.office_furniture_sales.dto.OrderDTO;
import com.officesales.office_furniture_sales.entity.Order;

public interface OrderService {
	
	// START - Order basic operations.
	OrderDTO createOrderForCustomer(Long customerId);
	OrderDTO addNewProductToOrder(Long orderId, Long productId, int quantity);
	OrderDTO updateItemQuantity(Long orderId, Long itemId, int quantity);
	OrderDTO removeProductFromOrder(Long orderId, Long itemId);
	OrderDTO getOrderDetails(Long orderId);
	// END - Order basic operations.
	
	Double calculateOrderTotalToOrder(Long orderId);
	
	// START - External services.
	boolean validateOrderAgainstExternalService(OrderDTO orderDTO);
	// END - External services.
}
