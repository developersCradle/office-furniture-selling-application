package com.officesales.office_furniture_sales.service;

import java.math.BigDecimal;

import com.officesales.office_furniture_sales.entity.Order;

public interface DiscountService {
	public BigDecimal calculateBestDiscountForGivenOrder(Order order);
}