package com.site.order_service.controller;

import com.site.order_service.entity.Orders;
import com.site.order_service.service.OrdersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    private final OrdersService ordersService;

    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    // save orders
    // POST http://localhost:9090/api/orders
    @PostMapping
    public Orders saveOrders(@RequestBody Orders orders) {
        return this.ordersService.saveOrders(orders);
    }

    // get all orders
    // GET http://localhost:9090/api/orders
    @GetMapping
    public List<Orders> getAllOrders() {
        return this.ordersService.getAllOrders();
    }

    // get Orders By Customer Id
    // GET http://localhost:9090/api/orders/customer/{customerId}
    @GetMapping("/customer/{customerId}")
    public List<Orders> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        return this.ordersService.getOrdersByCustomerId(customerId);
    }
}
