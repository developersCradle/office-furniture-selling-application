package com.officesales.office_furniture_sales.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  DTO for transferring only the necessary data from Entity. This inside business logic.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {
	private Long orderId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;  //tsekkaa nämä
    private String customerName; // Name of the customer who placed the order
    private Long customerId; // ID of the customer.
    
    private List<OrderItemInCartDTO> itemsInCart;
    private List<DiscountDTO> appliedDiscounts;
}