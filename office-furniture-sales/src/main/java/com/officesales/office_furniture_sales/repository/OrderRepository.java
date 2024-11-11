package com.officesales.office_furniture_sales.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.officesales.office_furniture_sales.entity.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}