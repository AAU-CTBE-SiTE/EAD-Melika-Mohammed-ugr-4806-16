package com.site.order_service.repo;

import com.site.order_service.entity.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// MongoRepository is the MongoDB version of JpaRepository.
// <Orders, String> = document type, type of the id
public interface OrdersRepo extends MongoRepository<Orders, String> {

    // Spring Data builds the query from the method name:
    // db.orders.find({ customerId: ? })
    List<Orders> findByCustomerId(Long customerId);
}
