package com.site.customer_service.service;

import com.site.customer_service.entity.Customer;
import com.site.customer_service.repo.CustomerRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepo customerRepo;

    // repo injected through the constructor
    public CustomerService(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    // save customer
    public Customer saveCustomer(Customer customer) {
        return this.customerRepo.save(customer);
    }

    // get all customers
    public List<Customer> getAllCustomers() {
        return this.customerRepo.findAll();
    }

    // get customer by id (404 if it doesn't exist)
    public Customer getCustomerById(Long id) {
        return this.customerRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Customer not found with id: " + id));
    }

    // update customer
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);
        existing.setName(customer.getName());
        existing.setEmail(customer.getEmail());
        return this.customerRepo.save(existing);
    }

    // delete customer
    public void deleteCustomer(Long id) {
        Customer existing = getCustomerById(id);
        this.customerRepo.delete(existing);
    }
}
