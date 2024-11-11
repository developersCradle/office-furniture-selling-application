package com.officesales.office_furniture_sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 *  DTO for transferring only the necessary data from Entity. This inside business logic.
 */

@Data
@AllArgsConstructor  
@NoArgsConstructor   
public class CustomerDTO {
    private Long id;
    private String name;
}