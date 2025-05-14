package com.example.customerapi.service;

import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import com.example.customerapi.util.Messages;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer createCustomer(Customer customer) {
        logger.info("Creating new customer: {}", customer.getEmail());
        customer.setId(null); // Ensure ID is auto-generated
        Customer savedCustomer = repository.save(customer);
        logger.info("Customer created with ID: {}", savedCustomer.getId());
        return savedCustomer;
    }

    public Customer getCustomerById(UUID id) {
        logger.info("Fetching customer by ID: {}", id);
        return repository.findById(id)
                .map(this::mapTier)
                .orElseThrow(() -> {
                    logger.error("Customer not found with ID: {}", id);
                    return new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND_ID + id);
                });
    }

    public Optional<Customer> getCustomerByName(String name) {
        logger.info("Fetching customer by name: {}", name);
        return repository.findByName(name).map(this::mapTier);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        logger.info("Fetching customer by email: {}", email);
        return repository.findByEmail(email).map(this::mapTier);
    }

    public List<Customer> getAllCustomers() {
        logger.info("Fetching all customers");
        List<Customer> customers = repository.findAll().stream()
                .map(this::mapTier)
                .toList();
        logger.info("Total customers found: {}", customers.size());
        return customers;
    }

    public Customer updateCustomer(UUID id, Customer updatedCustomer) {
        logger.info("Updating customer with ID: {}", id);
        Customer existing = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Cannot update. Customer not found with ID: {}", id);
                    return new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND_ID + id);
                });

        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setAnnualSpend(updatedCustomer.getAnnualSpend());
        existing.setLastPurchaseDate(updatedCustomer.getLastPurchaseDate());

        Customer savedCustomer = repository.save(existing);
        logger.info("Customer updated: {}", savedCustomer.getId());
        return savedCustomer;
    }

    public void deleteCustomer(UUID id) {
        logger.info("Deleting customer with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.error("Cannot delete. Customer not found with ID: {}", id);
            throw new EntityNotFoundException(Messages.CUSTOMER_NOT_FOUND_ID + id);
        }
        repository.deleteById(id);
        logger.info("Customer deleted with ID: {}", id);
    }

    private Customer mapTier(Customer customer) {
        BigDecimal spend = customer.getAnnualSpend() != null ? customer.getAnnualSpend() : BigDecimal.ZERO;
        LocalDate lastPurchase = customer.getLastPurchaseDate();
        LocalDate now = LocalDate.now();

        String tier = "Silver"; // default

        if (spend.compareTo(new BigDecimal("10000")) >= 0 &&
            lastPurchase != null &&
            lastPurchase.isAfter(now.minusMonths(6))) {
            tier = "Platinum";
        } else if (spend.compareTo(new BigDecimal("1000")) >= 0 &&
                   lastPurchase != null &&
                   lastPurchase.isAfter(now.minusMonths(12))) {
            tier = "Gold";
        }

        customer.setTier(tier);
        return customer;
    }
}
