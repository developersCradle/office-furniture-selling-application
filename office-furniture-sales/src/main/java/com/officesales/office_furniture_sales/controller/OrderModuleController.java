package com.officesales.office_furniture_sales.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.officesales.office_furniture_sales.dto.CustomerDTO;

@RestController
public class OrderModuleController {

    @GetMapping("/customers")
    public List<CustomerDTO> getAllCustomers() {
        return List.of( new CustomerDTO(1L,"nimi"), new CustomerDTO(2L,"nimi 2"));
    }
}