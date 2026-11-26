package com.site.order_service.service;

import com.site.order_service.entity.Orders;
import com.site.order_service.repo.OrdersRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepo ordersRepo;

    // repo injected through the constructor
    public OrdersService(OrdersRepo ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    // save orders
    public Orders saveOrders(Orders orders) {
        return this.ordersRepo.save(orders);
    }

    // get all orders
    public List<Orders> getAllOrders() {
        return this.ordersRepo.findAll();
    }

    // get orders by customer id
    public List<Orders> getOrdersByCustomerId(Long customerId) {
        return this.ordersRepo.findByCustomerId(customerId);
    }
}
