package com.officesales.office_furniture_sales.entity;



import java.math.BigDecimal;

import com.officesales.office_furniture_sales.shared.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * The Product table will represent a product in your system.
 */

@AllArgsConstructor  
@NoArgsConstructor
@Data
@Entity
@Table(name = "products")
public class Product {

	// Better to use Long for db practice.
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    private String name;
    private BigDecimal price; // Products prize.
    
    
    /*
     * Products can have an optional category. Null is no category specified.
     */
    
    @Enumerated(EnumType.STRING)  
    private Category category;    
}