package com.officesales.office_furniture_sales.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
 * Represents a specific product within an order process, in this context web-shop cart.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_items_in_cart")
public class OrderItemInCart {

	// Better to use Long for db practice.
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /*
     * nullable = false, so no null values. OrderItemInCart always need Order.
     * Many OrderItemInCart in one Order.
     * 
     * Owning Side of relationship.
     * OrderItemInCart table will have a column "order_id" for foreign key, reference to Order table.       
     */
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) 
    private Order order;

    /*
     *  Many OrderItemInCart pointing to one Product?
     *  Should quantity be raised rather than having extra entry?
     *  
     *  nullable = false, so no null values. OrderItemInCart always needs Product.
     * 	OrderItemInCart table will have a column "product_id" for foreign key, reference to Product table.
     */

    @ManyToOne 
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;

    private BigDecimal unitPrice;  // Price at the given time, when OrderItem was added to cart, not the total cart price.

}
