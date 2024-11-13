package com.officesales.office_furniture_sales.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


/*
 * The Customer table represents a customer in the system, which can have discount agreements.
 */
//TODO heikki, since discount was optional for customer we remove it here

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customers")
public class Customer {

	// Better to use Long for db practice

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    /*
     * List or Set?
     * Use list the order of items is critical, List will preserve insertion order.
     * Duplicate entries are acceptable.
     *
     * Set for fast lookup times and uniqueness.
     * 
     * The value of mappedBy "customer" refers to the name of the field in that owns the relationship, Entity named Order.
     * Inverse Side of relationship.
     * 
     * The value cascade = CascadeType.ALL ensures that when an Order is saved, deleted, or updated, all associated OrderItemInCart entities are also affected.
     * This was thought Entity or Repository manipulations alike.
     */
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders;

    // Helper methods to manage bidirectional relationship, adding Order to this Customer.
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this); 
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null); // Remember to add null when adding trough Entity helper class.
    }
    
    /* 
     * List or Set?
     * Use list the order of items is critical, List will preserve insertion order.
     * Duplicate entries are acceptable.
     *
     * Set for fast lookup times and uniqueness.
     * One Customer can have many Discount.
     * 
     * No logic at Entity level, this can be exception initializing collections at the entity level.
     * Some customers can have some Discount Agreements.
     */
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Discount> discountAgreements = new HashSet<>(); // Initialized to an empty set
    

}
