package com.officesales.office_furniture_sales.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.officesales.office_furniture_sales.dto.OrderDTO;
import com.officesales.office_furniture_sales.dto.OrderItemInCartDTO;
import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Order;
import com.officesales.office_furniture_sales.entity.OrderItemInCart;
import com.officesales.office_furniture_sales.entity.Product;
import com.officesales.office_furniture_sales.exception.ResourceNotFoundException;
import com.officesales.office_furniture_sales.repository.CustomerRepository;
import com.officesales.office_furniture_sales.repository.DiscountRepository;
import com.officesales.office_furniture_sales.repository.OrderRepository;
import com.officesales.office_furniture_sales.repository.ProductRepository;

/*
 * Responsible for handling the business logic related to orders.
 */

@Service
public  class OrderServiceImpl implements OrderService {
	
	 	@Autowired
	    private DiscountServiceImpl discountService; 
	 
	 	private final OrderRepository orderRepository;
	    private final DiscountRepository discountRepository;
	    private final ProductRepository productRepository;
	    private final CustomerRepository customerRepository;

	    @Autowired
	    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, DiscountRepository discountRepository, ProductRepository productRepository) { // Construction injection preferred!
	        this.orderRepository = orderRepository;
	        this.customerRepository = customerRepository;
	        this.discountRepository = discountRepository;
	        this.productRepository = productRepository;
	    }
    
	    public OrderDTO createOrderForCustomer(Long customerId) {
	    	
	    	Customer customer = customerRepository.findById(customerId)
	                  .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

	        Order order = new Order();
	        order.setCustomer(customer);
	        //order.setOrderDate(LocalDateTime.now()); // Date is set in db.
	        return convertToDTO(orderRepository.save(order));
	    }

	    @Override
	    public OrderDTO updateItemQuantity(Long orderId, Long orderItemInCartId, int quantity) {
	    	
	        Order order = orderRepository.findById(orderId)
	                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
	        
	        OrderItemInCart itemInCart = order.getOrderItems().stream() 
	        .filter(orderItemInCart -> orderItemInCart.getId().equals(orderItemInCartId)) // We want only that item, which was changed. 
	        .findFirst() // Get first, also Optional. This can return Empty one, but .orElseThrow() handles this. 
	        .orElseThrow(() -> new ResourceNotFoundException("Item with ID " + orderItemInCartId + " not found"));

	        // No need to call setter in Order because JPA will track changes made to the items in the collection. 
	        // Update the quantity to the OrderItemInCart.
	        itemInCart.setQuantity(quantity);

	        // Order has items.
	        orderRepository.save(order);
	        
	        // return Order, both references to order are the same object in memory. One saved to db, one who retried. 
	        return convertToDTO(order);
	    }
	    
	    //TODO think should put Transctional?

	    @Override
	    public OrderDTO addNewProductToOrder(Long orderId, Long productId, int quantity) {

	        Order order = orderRepository.findById(orderId)
	                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

	        // Product that wants to be added.
	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	        // If the product already exists in the order.
	        Optional<OrderItemInCart> existingItem = order.getOrderItems().stream()
	                .filter(item -> item.getProduct().getId().equals(productId))
	                .findFirst();

	        if (existingItem.isPresent()) {
	            // If the item already exists in the order, update the quantity.
	            OrderItemInCart item = existingItem.get();
	            item.setQuantity(item.getQuantity() + quantity); // Increase the quantity.
	        } else {
	            // If the product doesn't exist in the order, create a new item.
	            OrderItemInCart newItem = new OrderItemInCart();
	            newItem.setOrder(order);
	            newItem.setProduct(product);
	            newItem.setQuantity(quantity);
	            newItem.setUnitPrice(product.getPrice());
	            order.getOrderItems().add(newItem); // Add the new item to the order's item list.
	        }

	        // Save the updated order, all done here.
	        orderRepository.save(order);

	        return convertToDTO(order);
	    }
	    
	@Override
	public OrderDTO removeProductFromOrder(Long orderId, Long orderItemInCartId) {

		Order order = orderRepository.findById(orderId)
	            .orElseThrow(() -> new ResourceNotFoundException("Order not found with" + orderId));

	    // Find the item form Order to be removed.
	    OrderItemInCart orderItemInCart = order.getOrderItems().stream()
	            .filter(orderItem -> orderItem.getId().equals(orderItemInCartId))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException("OrderItemInCart was not found from given Order using" + orderId));
	    
	    //TOOD(heikki, error messages) error messages should be consistent.
	    
	    // Remove the item from the Order.
	    order.getOrderItems().remove(orderItemInCart);

	    // Save the updated order, will also delete since Cascading operator.
	    orderRepository.save(order);

	    return convertToDTO(order);
	}


	@Override
	public OrderDTO getOrderDetails(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public Double calculateOrderTotalToOrder(Long orderId) {
    	
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + orderId));

        Double total = order.getOrderItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice().doubleValue())
                .sum();
        
        BigDecimal bestDiscountByValue = discountService.calculateBestDiscountForGivenOrder(order);
        
        return total;
    }


	/*
	 * 	Mapping is straightforward (e.g., simple DTOs with a few fields), so converter is here.
	 */
	
	private OrderDTO convertToDTO(Order order) {
	    OrderDTO orderDTO = new OrderDTO();
	    orderDTO.setOrderId(order.getId());
	    orderDTO.setOrderDate(order.getOrderDate());
	    
	    // Customer should be always on Entity, so no null check.
	    orderDTO.setCustomerId(order.getCustomer().getId());
	    orderDTO.setCustomerName(order.getCustomer().getName());

	    if (order.getOrderItems() != null) {
	        List<OrderItemInCartDTO> itemsInCart = order.getOrderItems().stream()
	                .map(itemInCart -> {
	                    OrderItemInCartDTO itemInCartDTO = new OrderItemInCartDTO();
	                    itemInCartDTO.setOrderItemInCartId(itemInCart.getId());
	                    itemInCartDTO.setProductName(itemInCart.getProduct().getName());
	                    itemInCartDTO.setUnitPrice(itemInCart.getUnitPrice());
	                    itemInCartDTO.setQuantity(itemInCart.getQuantity());
	                    itemInCartDTO.setTotalPrice(itemInCart.getUnitPrice().multiply(BigDecimal.valueOf(itemInCart.getQuantity()))); //TODO in where total price should be checked?
	                    return itemInCartDTO;
	                })
	                .collect(Collectors.toList());
	        
	        orderDTO.setItemsInCart(itemsInCart);
	    }
	    
	    return orderDTO;
	}
}