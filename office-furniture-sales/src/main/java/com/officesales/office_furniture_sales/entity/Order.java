package com.officesales.office_furniture_sales.entity;




import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * This table stores customer orders like is shopping cart. Order linked with a customer.
 */

@AllArgsConstructor  
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

	// Better to use Long for db practice.
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    /*
     * nullable = false, so no null values. When order is placed, so is its Date.
     */
    
    @Column(nullable = false)
    private LocalDateTime orderDate; // Added orderDate for monthly grouping report.
    
    /*
     * nullable = false
     * so no null values. Order always needs Customer.
     * Many orders, for one customer. Customer can open multiple instances of web shop, so multiple order at same time.
     * 
     * Owning Side of relationship.
     * 
     * name = "customer_id"
     * for foreign key, reference to Customer table.       
     */
    
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    /*
     * List or Set?
     * Use list the order of items is critical, List will preserve insertion order.
     * Duplicate entries are acceptable.
     * Set for fast lookup times and uniqueness.
     * 
     * One Order, for many OrderItemInCart:s.
     * 
     * mappedBy "order" 
     * refers to the name of the field in that owns the relationship, Entity named OrderItemInCart.
     * Inverse Side of relationship.
     * 
     * cascade = CascadeType.ALL
     * ensures that when an Order is saved, deleted, or updated, all associated OrderItemInCart entities are also affected.
     * This was trough Entity or Repository manipulations alike.
     * 
     * orphanRemoval = true
     * If you remove an item from the orderItems collection, it will be deleted from the database as well
     */

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemInCart> orderItems = new HashSet<>(); // Initialized to an empty set.

    // TODO(Heikki, do i need add helper methods here to manage bidirectional relationship ) add when bidirectional relationship
    
    // Helper method to add OrderItem to Order.
    public void addOrderItem(OrderItemInCart orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);  // Set the order reference in OrderItemInCart.
    }

    // Helper method to remove OrderItem from Order.
    public void removeOrderItem(OrderItemInCart orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrder(null);  
    }
    
    // Ensures Date is set.
    @PrePersist
    public void prePersist() {
        if (this.orderDate == null) {
            this.orderDate = LocalDateTime.now();  // Set current date and time if not already set.
        }
    }
    
}