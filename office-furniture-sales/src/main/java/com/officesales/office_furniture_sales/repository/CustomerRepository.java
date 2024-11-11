package com.officesales.office_furniture_sales.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.entity.Product;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}