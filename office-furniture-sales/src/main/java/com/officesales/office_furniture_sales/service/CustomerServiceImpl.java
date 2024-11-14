package com.officesales.office_furniture_sales.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.officesales.office_furniture_sales.dto.CustomerDTO;
import com.officesales.office_furniture_sales.entity.Customer;
import com.officesales.office_furniture_sales.repository.CustomerRepository;

/*
 * Service for handling tasks related to managing customers. Example "there is a list of Customers".
 */

//TODO (heikki, return value) Add also customers can have some Discount Agreements.

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerRepository customerRepository;
	
	@Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) { // Construction injection preferred!
        this.customerRepository = customerRepository;
    }

	
	// DTO converter here for simplicity reasons. For now, we only need data for listing customers.
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        return customerDTO;
    }
	
    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}