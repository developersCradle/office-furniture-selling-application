package com.officesales.office_furniture_sales.entity;

import java.math.BigDecimal;

import com.officesales.office_furniture_sales.shared.DiscountType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Represents a discount applied to a customer or specific product.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discounts")
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * nullable = false, so no null values. Discount always needs Customer to belong or every discount is specific to a customer.
     */

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;  // To differentiate discount types.

    /*
     * nullable = true, so null values are allowed. Discount can have Product. The Product which this discount applies to, if no discount applies to Order. 
     */
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    /*
     * nullable = true, so null values are allowed. This has null when discount is buy x, pay y.
     * BigDecimal over Integer was chosen because of nature of financial application. Store as 0.10 for 10% or 0.025 for 25%.
     */
    
    //TODO(heikki, add validation) Make sure, discount is not saved as Integer values! 
    // Ensure the discount value is between 0 and 1 (i.e., between 0% and 100%)
    // @DecimalMin(value = "0.00", inclusive = true, message = "Discount cannot be less than 0%.")
    // @DecimalMax(value = "1.00", inclusive = true, message = "Discount cannot be greater than 100%.")
    @Column(nullable = true)
    private BigDecimal discountValue;  // Used only percentage. This one is null, when discount type is "Buy X, Pay Y", otherwise its % value.
    
    // Fields for Buy X, Pay Y discount logic.
    @Column(name = "buy_amount")
    private Integer buyAmount; // For "Buy X". Example Buy 3".
    
    @Column(name = "pay_amount")
    private Integer payAmount; // For "Pay Y". Example Pay for 2".

    /* 
     * TODO(heikki, saved in session or in db. Open Depate) This should be done in front end session level, and Discount removed when pressed "Bought"?
     * Discount can be left to db for data scientist to analyze later.
     * 
     * nullable = false, so no null values.
     * We want enforce only true or false.
     * 
     * boolean if discount is applied or not.
     */
    @Column(name = "is_applied")
    private boolean isApplied = false;

    
    
}