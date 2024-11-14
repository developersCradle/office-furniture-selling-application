package com.officesales.office_furniture_sales.dto;

import java.math.BigDecimal;

import com.officesales.office_furniture_sales.shared.DiscountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  
@NoArgsConstructor 
public class DiscountDTO {
    private BigDecimal discountValue;     // Value or amount of the discount (could be percentage or amount)
    private DiscountType discountType;    // Enum to specify the type of discount, e.g., PERCENTAGE, FLAT, etc.
    private String applicableProduct;     // Optional: the product name if this discount is product-specific

}
