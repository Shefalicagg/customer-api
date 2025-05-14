package com.example.customerapi.controller;

import com.example.customerapi.model.Customer;
import com.example.customerapi.service.CustomerService;
import com.example.customerapi.util.Messages;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        logger.info("Creating customer: {}", customer);
        if (customer.getId() != null) {
            logger.warn("Bad request - ID must not be provided");
            return ResponseEntity.badRequest().build();
        }
        Customer created = service.createCustomer(customer);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        logger.info("Fetching customer with ID: {}", id);
        Customer customer = service.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity<?> getCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "email", required = false) String email) {

        if (name != null) {
            logger.info("Fetching customer by name: {}", name);
            return service.getCustomerByName(name)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        if (email != null) {
            logger.info("Fetching customer by email: {}", email);
            return service.getCustomerByEmail(email)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }

        List<Customer> customers = service.getAllCustomers();
        if (customers.isEmpty()) {
            logger.info("No customers found");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable UUID id,
                                                   @Valid @RequestBody Customer customer) {
        logger.info("Updating customer with ID: {}", id);
        Customer updated = service.updateCustomer(id, customer);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        logger.info("Deleting customer with ID: {}", id);
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
