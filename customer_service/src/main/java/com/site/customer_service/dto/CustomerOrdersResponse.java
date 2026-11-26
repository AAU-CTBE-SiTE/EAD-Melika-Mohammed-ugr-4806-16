package com.site.customer_service.dto;

import com.site.customer_service.entity.Customer;

import java.util.List;

// Answer to "who is the customer, how many orders did they place, and what did they order?"
public record CustomerOrdersResponse(
        Customer customer,
        int totalOrders,
        List<OrderDto> orders
) {
}
