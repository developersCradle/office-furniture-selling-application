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
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Represents a discount applied to a customer or specific product.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
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
     * nullable = true, so null values are allowed. Discount can have Product. The Product this discount applies to, if no it applies to Order. 
     */
    
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Column(nullable = false)
    private BigDecimal discountValue;  // Used for percentage or fixed amount discount.
    
    // Fields for Buy X, Pay Y discount logic.
    @Column(name = "buy_amount")
    private Integer buyAmount; // For "Buy X". Example Buy 3".
    
    @Column(name = "pay_amount")
    private Integer payAmount; // For "Pay Y". Example Pay for 2".

    
}