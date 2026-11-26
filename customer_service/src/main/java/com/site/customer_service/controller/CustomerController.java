package com.site.customer_service.controller;

import com.site.customer_service.dto.CustomerOrdersResponse;
import com.site.customer_service.dto.OrderDto;
import com.site.customer_service.entity.Customer;
import com.site.customer_service.service.CustomerService;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final RestClient restClient;

    // both beans injected through the constructor:
    // CustomerService (our @Service) and RestClient (the @Bean from CustomerConfig)
    public CustomerController(CustomerService customerService, RestClient restClient) {
        this.customerService = customerService;
        this.restClient = restClient;
    }

    // save customer
    // POST http://localhost:8080/api/customers
    @PostMapping
    public Customer saveCustomer(@RequestBody Customer customer) {
        return this.customerService.saveCustomer(customer);
    }

    // get all customers
    // GET http://localhost:8080/api/customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return this.customerService.getAllCustomers();
    }

    // get customer by id
    // GET http://localhost:8080/api/customers/{id}
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return this.customerService.getCustomerById(id);
    }

    // update customer
    // PUT http://localhost:8080/api/customers/{id}
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return this.customerService.updateCustomer(id, customer);
    }

    // delete customer
    // DELETE http://localhost:8080/api/customers/{id}
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        this.customerService.deleteCustomer(id);
        return "Customer deleted with id: " + id;
    }

    // get customer orders  (AIM of the lab: inter-service communication)
    // GET http://localhost:8080/api/customers/{id}/orders
    // customer comes from MySQL, orders come from order_service (MongoDB) via RestClient
    @GetMapping("/{id}/orders")
    public CustomerOrdersResponse getCustomerOrders(@PathVariable("id") Long id) {

        // 1. find the customer in MySQL (404 if not found)
        Customer customer = this.customerService.getCustomerById(id);

        // 2. call order_service with the FULL URL
        List<OrderDto> orders = this.restClient.get()
                .uri("http://localhost:9090/api/orders/customer/" + id)
                .retrieve()
                .body(new ParameterizedTypeReference<List<OrderDto>>() {});

        if (orders == null) {
            orders = List.of();
        }

        // 3. return the customer + how many orders + what they ordered
        return new CustomerOrdersResponse(customer, orders.size(), orders);
    }
}
