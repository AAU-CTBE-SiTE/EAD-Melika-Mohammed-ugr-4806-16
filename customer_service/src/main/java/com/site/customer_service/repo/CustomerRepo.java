package com.site.customer_service.repo;

import com.site.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository gives save(), findAll(), findById(), deleteById() ... for free.
// <Customer, Long> = entity type, type of the id
public interface CustomerRepo extends JpaRepository<Customer, Long> {
}
