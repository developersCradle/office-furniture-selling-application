package com.officesales.office_furniture_sales.shared;

/*
 * Represents different types of Discounts.
 * 
 * We use enum in DiscountType since they are fixed and small.
 * DiscountType as Entity in other hand would bring flexibility.
 * 
 * DiscountType is inside shared package since, service layer is needing this for business logic.
 */

public enum DiscountType {
    PERCENTAGE,      // Percentage discount example 15% off.
    FIXED_AMOUNT,    // Fixed amount discount example 10€ off.
    BUY_X_PAY_Y   	 // "Buy X, pay for Y".
}