package com.officesales.office_furniture_sales.shared;

/*
 * Represents different types of office furniture.
 * 
 * We use enum in Categories since they are  fixed and small.
 * Category as Entity in other hand would bring flexibility.
 * 
 * Category is inside shared package since, service layer is needing this for business logic.
 */

public enum Category {
    CHAIRS,
    DESKS,
    TABLES,
    CARPETS,
    FRIDGE_FOR_ENERGY_DRINKS // This is needed!
};