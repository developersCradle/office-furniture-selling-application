package com.officesales.office_furniture_sales.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Discount;
import com.officesales.office_furniture_sales.entity.Order;
import com.officesales.office_furniture_sales.entity.OrderItemInCart;
import com.officesales.office_furniture_sales.entity.Product;
import com.officesales.office_furniture_sales.shared.Category;
import com.officesales.office_furniture_sales.shared.DiscountType;

@ExtendWith(MockitoExtension.class)
class DiscountServiceImplTest {

	@InjectMocks
	private DiscountServiceImpl discountService;

	@Test
	void testCalculateBestDiscountForGivenOrder_PercentageDiscountOnOrder() {

		// START - Given.

		Customer customer = new Customer();
		Discount discount = new Discount();
		discount.setDiscountType(DiscountType.PERCENTAGE);
		discount.setDiscountValue(new BigDecimal("0.10")); // 10% discount.
		Set<Discount> discounts = new HashSet<>();
		discounts.add(discount);

		Product product = new Product();
		product.setName("Office Chair");
		product.setCategory(Category.CHAIRS);

		Product product2 = new Product();
		product.setName("Nocco Fridge");
		product.setCategory(Category.FRIDGE_FOR_ENERGY_DRINKS);

		OrderItemInCart orderItem = new OrderItemInCart();
		orderItem.setProduct(product);
		orderItem.setUnitPrice(new BigDecimal("200.00"));
		orderItem.setQuantity(2);

		OrderItemInCart orderItem2 = new OrderItemInCart();
		orderItem2.setProduct(product2);
		orderItem2.setUnitPrice(new BigDecimal("350.00"));
		orderItem2.setQuantity(4);

		Order order = new Order();
		order.setCustomer(customer);
		order.addOrderItem(orderItem);
		order.addOrderItem(orderItem2);

		customer.setDiscountAgreements(discounts);

		// END - Given.

		// START - When.

		BigDecimal actual = discountService.calculateBestDiscountForGivenOrder(order);

		// END - When.

		// START - Then.

		BigDecimal expected = new BigDecimal("180.00"); // 1800 * 0.1 = 1080. So 10% off from whole Order.
		assertEquals(expected.doubleValue(), actual.doubleValue(), 0.0001); // We are using doubles for comparing for
																			// decimal simplicity.

		// END - Then.
	}

    @Test
    void testcalculateBestDiscountForGivenOrder_PercentageDiscountOnSpecificProduct() {

    	// START - Given.
    	Customer customer = new Customer();
        Product product = new Product();
        product.setName("Office Desk");
        product.setCategory(Category.DESKS);
        
        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDiscountValue(new BigDecimal("0.20")); // 20% discount
        discount.setProduct(product);

        OrderItemInCart orderItem = new OrderItemInCart();
        orderItem.setProduct(product);
        orderItem.setUnitPrice(new BigDecimal("500.00"));
        orderItem.setQuantity(1);

        Order order = new Order();
        order.setCustomer(customer);
        order.addOrderItem(orderItem);

        // Setting discount differently this time.
        customer.setDiscountAgreements(Set.of(discount)); 
        // END - Given.
        
        // START - When.
        
        BigDecimal actual = discountService.calculateBestDiscountForGivenOrder(order);
        
        // END - When.
        
        // START - Then.
        
        BigDecimal expected = new BigDecimal("100.00"); // 500 * 0.2 = 100. So 20% off from specific Product.
        
		assertEquals(expected.doubleValue(), actual.doubleValue(), 0.0001); // We are using doubles for comparing for
																			// decimal simplicity.
		
        // END - Then.
    }

        @Test
        void testcalculateBestDiscountForGivenOrder_BuyXPayY() {
        	
        // START - Given.
        Customer customer = new Customer();
        Product product = new Product();
        product.setName("NOCCO master");
        product.setCategory(Category.FRIDGE_FOR_ENERGY_DRINKS);
        
        Discount discount = new Discount();
        discount.setDiscountType(DiscountType.BUY_X_PAY_Y);
        discount.setProduct(product);
        discount.setBuyAmount(3);
        discount.setPayAmount(2);

        OrderItemInCart orderItem = new OrderItemInCart();
        orderItem.setProduct(product);
        orderItem.setUnitPrice(new BigDecimal("30.00"));
        orderItem.setQuantity(6); // Eligible for 2 free items.

        Order order = new Order();
        order.addOrderItem(orderItem);
        order.setCustomer(customer);

        customer.setDiscountAgreements(Set.of(discount));
        
        // END - Given.
        
        // START - When.
        
        BigDecimal actual = discountService.calculateBestDiscountForGivenOrder(order);
        
        // END - When.
        
        // START - Then.
        
        BigDecimal expected = new BigDecimal("60.00"); // 2 free items worth 30 each = 60.
        
        assertEquals(expected.doubleValue(), actual.doubleValue(), 0.0001); // We are using doubles for comparing for decimal simplicity.
        
        // END - Then.
        
        }
        
        //TODO(Heikki, add more test ) Exception  NoMatchingProduct

}
