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
    private LocalDateTime orderDate; //TODO lissää Entityyn tämä
    private BigDecimal totalAmount;  //tsekkaa nämä
    private List<OrderItemInCartDTO> items;
    private List<DiscountDTO> appliedDiscounts;
}