package com.officesales.office_furniture_sales.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.officesales.office_furniture_sales.dto.DiscountDTO;
import com.officesales.office_furniture_sales.dto.OrderDTO;
import com.officesales.office_furniture_sales.dto.OrderItemInCartDTO;
import com.officesales.office_furniture_sales.service.OrderServiceImpl;
import com.officesales.office_furniture_sales.shared.DiscountType;

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
    	
    	//GET THIS WORKING
    	// OrderDTO orderDTO = orderService.getOrderDetails(orderId);

    	
    	// DUMMY START - This will change take real Order from db. To get report working.
    	
    	OrderDTO orderDTO = new OrderDTO();
        
        orderDTO.setOrderId(1L);
        orderDTO.setOrderDate(LocalDateTime.now());
        
        OrderItemInCartDTO item1 = new OrderItemInCartDTO();
        item1.setProductName("Office Chair");
        item1.setUnitPrice(new BigDecimal("75.00"));
        item1.setQuantity(1);
        
        OrderItemInCartDTO item2 = new OrderItemInCartDTO();
        item2.setProductName("Desk Lamp");
        item2.setUnitPrice(new BigDecimal("25.00"));
        item2.setQuantity(3);
        
        List<OrderItemInCartDTO> items = new ArrayList<>(Arrays.asList(item1, item2));
        orderDTO.setItemsInCart(items);

        DiscountDTO discount1 = new DiscountDTO();
        discount1.setDiscountType(DiscountType.PERCENTAGE);
        discount1.setDiscountValue(new BigDecimal("10.00"));
        
        DiscountDTO discount2 = new DiscountDTO();
        discount2.setDiscountType(DiscountType.BUY_X_PAY_Y);
        discount2.setDiscountValue(new BigDecimal("25.00"));
        
        
    	// DUMMY END - This will change take real Order from db.
    	
        model.addAttribute("order", orderDTO); // Adding to model.
        model.addAttribute("totalAmount", 200);
        // model.addAttribute("totalAmount", orderService.calculateTotalOrderPrice(orderId)); // Adding to model
        
        return "orderDetails"; // Thymeleaf will go look for orderDetails.html
    }
    
  //TODO (heikki, end point for return text ) end point getOrderDetailsAsText with some cool StringBuilder logic!
}