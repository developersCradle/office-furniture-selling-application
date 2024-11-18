package com.officesales.office_furniture_sales.controller;


import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.officesales.office_furniture_sales.dto.OrderDTO;
import com.officesales.office_furniture_sales.service.OrderServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
    
    /*
     * Removes a specific Product with all of its instances of OrderItemInCart from an Order. 
     */
    
    @DeleteMapping("/{orderId}/items/{orderItemInCartId}")
    public ResponseEntity<OrderDTO> removeProductFullyFromOrder(
        @PathVariable Long orderId,
        @PathVariable Long orderItemInCartId
    ) {
        OrderDTO updatedOrder = orderService.removeProductFromOrder(orderId, orderItemInCartId);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }

    /*
     * Remove later.
     * Testing end point calculating the total price of an order, taking into account the discount agreement.
     */
    
    @GetMapping("/{orderId}/total")
    public ResponseEntity<BigDecimal> calculateTotalOrderPrice(@PathVariable Long orderId) {
        BigDecimal totalPrice = orderService.calculateTotalOrderPrice(orderId);
        return ResponseEntity.ok(totalPrice);
    }
    

    /*
     * Adds a totally new Product as an OrderItemInCart to an existing Order. 
     */
    
    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderDTO> addProductToOrder(
        @PathVariable Long orderId,
        @RequestParam Long productId,
        @RequestParam int quantity
    ) {
    	//Todo laita @valid
    	OrderDTO updatedOrder = orderService.addNewProductToOrder(orderId, productId, quantity);
    	
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }
    
    /*
     *  Create an Order for a specific Customer.
     */
    
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<OrderDTO> createOrderForCustomer(
            @PathVariable Long customerId) {
        
        // Create order for the customer using the service.
        OrderDTO createdOrder = orderService.createOrderForCustomer(customerId);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    
    /*
     * Updates the quantity of a specific OrderItemInCart in an Order.
     */
    
    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<OrderDTO> updateOrderItemQuantity(
        @PathVariable Long orderId,
        @PathVariable Long orderItemInCartId,
        @RequestParam int quantity
    ) {
    	//Update quantity.
    	OrderDTO updatedOrder = orderService.updateItemQuantity(orderId, orderItemInCartId, quantity);
        return ResponseEntity.status(HttpStatus.OK).body(updatedOrder);
    }
}
    

