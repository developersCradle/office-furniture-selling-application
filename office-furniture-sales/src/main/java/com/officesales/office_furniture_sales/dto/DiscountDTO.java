package com.officesales.office_furniture_sales.dto;

import java.math.BigDecimal;

import com.officesales.office_furniture_sales.shared.DiscountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  DTO for transferring only the necessary data from Entity. This inside business logic.
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DiscountDTO {
    private BigDecimal discountValue;     // Value or amount of the discount (percentage )
    private DiscountType discountType;    // Enum to specify the type of discount.
//  private String applicableProduct;     // Optional, points to Product if Product Discount.
}
