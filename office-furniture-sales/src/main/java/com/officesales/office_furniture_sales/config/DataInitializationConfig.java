package com.officesales.office_furniture_sales.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Discount;
import com.officesales.office_furniture_sales.entity.Order;
import com.officesales.office_furniture_sales.entity.OrderItemInCart;
import com.officesales.office_furniture_sales.entity.Product;
import com.officesales.office_furniture_sales.repository.CustomerRepository;
import com.officesales.office_furniture_sales.repository.DiscountRepository;
import com.officesales.office_furniture_sales.repository.OrderRepository;
import com.officesales.office_furniture_sales.repository.ProductRepository;
import com.officesales.office_furniture_sales.shared.Category;
import com.officesales.office_furniture_sales.shared.DiscountType;

/*
 * This one for startup some dummy data on db! These need to saved in way from up to down for Entities which have more complex ORM joins.
 * This will be used for showing its working before Integration Tests or Units Test are introduced! This no means clean, but for get test data going!
 */

@Configuration
public class DataInitializationConfig {

	@Bean
	CommandLineRunner initDatabase(ProductRepository productRepository, CustomerRepository customerRepository, DiscountRepository discountRepository, OrderRepository orderRepository) {
		return args -> {

			// START - Adding some dummy Products for database.
			
			List<Product> products = List.of(

					new Product(null, "Executive Office Chair", new BigDecimal("250.00"), Category.CHAIRS),
					new Product(null, "Adjustable Desk", new BigDecimal("700.00"), Category.DESKS),
					new Product(null, "Round Meeting Table", new BigDecimal("350.00"), Category.TABLES),
					new Product(null, "Big GOOD carpet!", new BigDecimal("350.00"), Category.CARPETS),
					new Product(null, "FRIDGE FOR NOCCO!", new BigDecimal("50.00"), Category.FRIDGE_FOR_ENERGY_DRINKS),
					new Product(null, "FRIDGE FOR RED BULL!", new BigDecimal("30.00"),
							Category.FRIDGE_FOR_ENERGY_DRINKS),
					new Product(null, "IKEA MAGIC", new BigDecimal("10.00"), null) // No category since, its optional!
			);

			productRepository.saveAll(products);

			// END - Adding some dummy Products for database.
			
			// START - Adding some dummy Customer with Discounts for database.

			// Create customers.
			Customer customer1 = new Customer();
			customer1.setName("Aku Ankka");

			Customer customer2 = new Customer();
			customer2.setName("Miina Ankka");

			Customer customer3 = new Customer(); // Does not have discounts!
			customer3.setName("Alennukseton Ankka");
			
			Customer customer4 = new Customer(); 
			customer4.setName("Alennus Ankka");
			
			Customer customer5 = new Customer(); // This duck is Black Hole for discount!!!!
			customer5.setName("Tokmanni Sikapäivä Halpahalli Ankka"); // Sitä halpuuden määrää! Lot of discounts!
			
			// Save customers to the database.
			customerRepository.save(customer1);
			customerRepository.save(customer2);
			customerRepository.save(customer3);
			customerRepository.save(customer4); //TODO(Heikki, make more test cases) Do these test cases for discounts.
			customerRepository.save(customer5); //TODO(Heikki, make more test cases) Do these test cases for discounts. Lot of discount this one.
			

			// Create discounts for customers.
			Discount percent_discount_for_order = new Discount();
			percent_discount_for_order.setCustomer(customer1); // Discount for Aku Ankka.
			percent_discount_for_order.setDiscountType(DiscountType.PERCENTAGE);
			percent_discount_for_order.setDiscountValue(new BigDecimal("0.10")); // 10% off.
			percent_discount_for_order.setProduct(null); // null here. Setting this discount for Order, NOT to Product. It will affect full Order!
			
			Discount buy_x_pay_y_discount = new Discount();
			buy_x_pay_y_discount.setCustomer(customer2); // Discount for Miina Ankka.
			buy_x_pay_y_discount.setDiscountType(DiscountType.BUY_X_PAY_Y); // This only applies for specific product!
			
			buy_x_pay_y_discount.setProduct(products.get(4)); // Discount only applies for FRIDGE FOR NOCCO!
			buy_x_pay_y_discount.setBuyAmount(3);  // Buy 3 items.
			buy_x_pay_y_discount.setPayAmount(2);  // Pay for only 2 items.

			 // Save discounts to the database.
            discountRepository.save(percent_discount_for_order);
            discountRepository.save(buy_x_pay_y_discount);
            
			// END - Adding some dummy Customer with Discounts for database.
            
            // START - Adding some dummy Order with some OrderItemInCart for database. Associated with Customers which have different types of Discounts.
            
            Order order1 = new Order();
            order1.setCustomer(customer1);
            
            Product chair = products.get(0);   // Executive Office Chair.
            Product desk = products.get(1);    // Adjustable Desk.
            
            OrderItemInCart orderItem1 = new OrderItemInCart();
            orderItem1.setProduct(chair); // Chair was chosen.
            orderItem1.setQuantity(2); // Two units were clicked from the web site.
            orderItem1.setUnitPrice(chair.getPrice()); // Price was stored from the moment of clicking. So if 😈 company would change the prize on the fly, it would not get affected!
            
            OrderItemInCart orderItem2 = new OrderItemInCart();
            orderItem2.setProduct(desk); // Desk was chosen.
            orderItem2.setQuantity(1); // One unit were clicked from the web site.
            orderItem2.setUnitPrice(desk.getPrice()); // Price was stored from the moment of clicking. So if 😈 company would change the prize on the fly, it would not get affected!
            
            order1.addOrderItem(orderItem1);
            order1.addOrderItem(orderItem2);

            // Save the order.
            orderRepository.save(order1);

            
            Order order2 = new Order();
            order2.setCustomer(customer2);
            
            // ENRGY DRINK KEEPS YOU CODING.
            Product fridge_for_nocco = products.get(4); // FRIDGE FOR NOCCO!
            Product fridge_for_red_bull = products.get(5); // FRIDGE FOR RED BULL!
            // ENRGY DRINK KEEPS YOU CODING.
            
            OrderItemInCart orderItem3 = new OrderItemInCart();
            orderItem3.setProduct(fridge_for_nocco); // Fridge for NOCCO:s was chosen.
            orderItem3.setQuantity(3); // Three units were clicked from the web site.
            orderItem3.setUnitPrice(fridge_for_nocco.getPrice()); // Price was stored from the moment of clicking. So if 😈 company would change the prize on the fly, it would not get affected!
            
            OrderItemInCart orderItem4 = new OrderItemInCart();
            orderItem4.setProduct(fridge_for_red_bull); // Fridge for RED BULL:s was chosen.
            orderItem4.setQuantity(2); // Two units were clicked from the web site.
            orderItem4.setUnitPrice(fridge_for_red_bull.getPrice()); // Price was stored from the moment of clicking. So if 😈 company would change the prize on the fly, it would not get affected!
            
            order2.addOrderItem(orderItem3);
            order2.addOrderItem(orderItem4);

            // Save the order.
            orderRepository.save(order2); // Customer with "Buy 3 Pay 2" Discount for NOCCO FRIDGE!

            
            Order order3 = new Order();
            order3.setCustomer(customer3);
            
            Product no_category_ikea = products.get(6); // Where is my tools IKEA?!

            OrderItemInCart orderItem5 = new OrderItemInCart();
            orderItem5.setProduct(no_category_ikea); // IKEA was chosen because you wen't with your girlfriend to web store.
            orderItem5.setQuantity(10); // I want it all! Units were clicked from the web site.
            orderItem5.setUnitPrice(no_category_ikea.getPrice()); // Price was stored from the moment of clicking. So if 😈 company would change the prize on the fly, it would not get affected!
            
            order3.addOrderItem(orderItem5);

            // Save the order
            orderRepository.save(order3); // Customer with no Discount or Product with no category.

            // END - Adding some dummy Order with some OrderItemInCart for database. Associated with Customers which have different types of Discounts.

		};
	}
}