package com.officesales.office_furniture_sales.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Discount;
import com.officesales.office_furniture_sales.entity.Order;

/*
 * Responsible handling discount calculations and its actions.
 */

@Service
public class DiscountServiceImpl implements DiscountService {
	
	/*
	 * Return highest valued Discount. In this business logic, only one discount can apply at the time, regardless where it is going to be applied.
	 */
	
	 public BigDecimal calculateBestDiscountForGivenOrder(Order order) {
		 
		 	Customer customer = order.getCustomer(); // Getting Customer from current Order. Order always needs to have some Customer, so no need for null checks.
		 	
	        // Retrieve Discounts associated with the Customer.
	        Set<Discount> discounts = customer.getDiscountAgreements(); 
	        
	        return discounts.stream().map(discount -> calculateDiscountValue(order, discount))
	        		   .max(Comparator.naturalOrder())  // Maximum value in natural order for BigDecimal.                           
	                   .orElse(BigDecimal.ZERO); 
	        
	    }

	 /*
	  * Calculate amount of value in money Customer gets using given discount. Discount can be affected to whole Order or to Product.
	  */

	 private BigDecimal calculateDiscountValue(Order placedOrder, Discount discount) {
	        
	        switch (discount.getDiscountType()) {
	        
	            case PERCENTAGE:
	            	// Apply percentage Discount for some specific Product.
	                if (discount.getProduct() != null) { // When Product is set in Discount and type is PERCENTAGE, it means % from specific Product.
	                	
	                	BigDecimal unitPriceOfProductWithoutAnyDiscount = placedOrder.getOrderItems().stream()
	                		    .filter(item -> item.getProduct().equals(discount.getProduct())) // We found Product inside Order and inside Discounts.
	                		    .map(item -> item.getUnitPrice()) // Get the unit price of the Product, when it was clicked into Order.
	                		    .findFirst() // Get the first match, since % off from Product works only for one Product.
	                		    .orElse(BigDecimal.ZERO); // If no match found, set the total as zero.
	                	
	                    // Calculate the discount amount.
	                	return unitPriceOfProductWithoutAnyDiscount.multiply(discount.getDiscountValue());
	                	
	                }
	                

	                // Apply percentage Discount from the Order total.
	                return placedOrder.getOrderItems().stream()
	                	                .map(item -> item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()))) // Unit price(when was clicked) * quantity = total. This discount applied to whole Order.
	                	                .reduce(BigDecimal.ZERO, BigDecimal::add) // Start from 0 and count all values total.
	                					.multiply(discount.getDiscountValue()); 
	            case BUY_X_PAY_Y:
	                // Example of "Buy X, Pay for Y" discount on a specific product.
	            	return placedOrder.getOrderItems().stream()
	                        .filter(item -> item.getProduct().equals(discount.getProduct())) // Discounted Product is the same as in Discount.
	                        .findFirst() // Get that OrderItemInCart to calculation.
	                        .map(orderItemInCalculation -> { 
	                        	
	                        	// Validation: Ensure payAmount (Y) is not greater than buyAmount (X)
	                            if (discount.getPayAmount() > discount.getBuyAmount()) {
	                                throw new IllegalArgumentException("Invalid discount parameters: Pay amount cannot be greater than Buy amount.");
	                            }
	                        	
	                            
	                        	// START - Calculation takes place here.
	                            int fullSetsOfBuyX = orderItemInCalculation.getQuantity() / discount.getBuyAmount(); // Example. User has selected 5 OrderItemInCart, Discount is "Buy 2". User fits the Discount two times.
								int howManyFreeItemsForThisSet = discount.getBuyAmount() - discount.getPayAmount(); //Example. Buy 3 and Pay 2 will be one free item.
								int freeItems = fullSetsOfBuyX * howManyFreeItemsForThisSet; // Amount of free items in this deal.
	                            return orderItemInCalculation.getUnitPrice().multiply(BigDecimal.valueOf(freeItems)); // Value of free items.
	                            // END - Calculation takes place here.
	                        })
	                        .orElse(BigDecimal.ZERO); // If no match found, set the total as zero.
	                
	            default:
	                throw new UnsupportedOperationException("Unknown discount type: " + discount.getDiscountType());
	        }
	    }
	    
}