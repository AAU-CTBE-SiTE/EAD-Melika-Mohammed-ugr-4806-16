package com.site.customer_service.dto;

// Same fields as the Orders document in order_service.
// customer_service has no Orders entity, so this class just holds
// the JSON that order_service sends back.
public record OrderDto(
        String id,          // MongoDB id of the order
        Long customerId,    // MySQL id of the customer
        String productName,
        int quantity,
        double price
) {
}
