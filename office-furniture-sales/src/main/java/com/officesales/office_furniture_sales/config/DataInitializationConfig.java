package com.officesales.office_furniture_sales.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.officesales.office_furniture_sales.entity.Product;
import com.officesales.office_furniture_sales.entity.shared.Category;
import com.officesales.office_furniture_sales.repository.ProductRepository;

/*
 * This one for startup some dummy data on db!
 */
@Configuration
public class DataInitializationConfig {

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return args -> {
            List<Product> products = List.of(
            		
                new Product(null, "Executive Office Chair", new BigDecimal("250.00"), Category.CHAIRS),
                new Product(null, "Adjustable Desk", new BigDecimal("700.00"), Category.DESKS),
                new Product(null, "Round Meeting Table", new BigDecimal("350.00"), Category.TABLES),
                new Product(null, "Big GOOD carpet!", new BigDecimal("350.00"), Category.CARPETS),
                new Product(null, "FRIDGE FOR NOCCO!", new BigDecimal("50.00"), Category.FRIDGE_FOR_ENERGY_DRINKS),
                new Product(null, "FRIDGE FOR RED BULL!", new BigDecimal("30.00"), Category.FRIDGE_FOR_ENERGY_DRINKS),
                new Product(null, "IKEA MAGIC", new BigDecimal("10.00"), null) // No category since, its optional!
            );

            productRepository.saveAll(products);
        };
    }
}