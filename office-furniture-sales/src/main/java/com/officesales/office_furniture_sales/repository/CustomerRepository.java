package com.officesales.office_furniture_sales.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Product;

/*
 * Using Repository over DAO, since its more common and its remade Spring family.
 * Reminder for myself to use Longs ID for best practices in ORM frameworks like JPA.
 * 
 * This class is for initializing some type of Customer for database.
 */

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}