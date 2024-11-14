package com.officesales.office_furniture_sales.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  DTO for transferring only the necessary data from Entity. This inside business logic.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemInCartDTO {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice; //When was clicked into basked in web shop.
    private BigDecimal totalPrice;   // The total price for this item (unitPrice * quantity).


}