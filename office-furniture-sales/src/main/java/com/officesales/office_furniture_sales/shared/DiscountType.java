package com.officesales.office_furniture_sales.shared;

/*
 * Represents different types of Discounts.
 * 
 * We use enum in DiscountType since they are fixed and small.
 * DiscountType as Entity in other hand would bring flexibility.
 * 
 * DiscountType is inside shared package since, service layer is needing this for business logic.
 */

//TODO (heikki, add all types here) Add all discount possibilities here. I will be easier to scale more discounts.
public enum DiscountType {
    PERCENTAGE,      // Percentage Discount example 15% off from Product or from Order.
    BUY_X_PAY_Y   	 // Unit Discount "Buy X, pay for Y".
}