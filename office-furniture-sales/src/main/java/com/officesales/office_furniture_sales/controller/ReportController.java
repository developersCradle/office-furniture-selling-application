package com.officesales.office_furniture_sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.officesales.office_furniture_sales.dto.OrderDTO;
import com.officesales.office_furniture_sales.service.OrderServiceImpl;

/* 
 * Controller for generating and retrieving reports related to orders.
 * Provides end points for viewing order details in different formats (HTML, Text, etc.).
 */

@Controller
public class ReportController {

    private final OrderServiceImpl orderService;

    @Autowired
    public ReportController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }
    

    /*
     * Retrieves a detailed report for the specified order and returns a view for display.
     */
    
    @GetMapping("/order/report/{orderId}")
    public String getOrderDetailsReport(@PathVariable Long orderId, Model model) {
    	
    	 OrderDTO orderDTO = orderService.getOrderDetails(orderId);

        model.addAttribute("order", orderDTO); // Adding to model.
        model.addAttribute("totalAmount", orderService.calculateTotalOrderPrice(orderId));
        
        return "orderDetails"; // Thymeleaf will go look for orderDetails.html
    }
    
  //TODO (heikki, end point for return text ) end point getOrderDetailsAsText with some cool StringBuilder logic!
}