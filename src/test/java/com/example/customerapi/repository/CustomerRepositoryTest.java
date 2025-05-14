package com.example.customerapi.repository;

import com.example.customerapi.model.Customer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    void testSaveAndFindById() {
        Customer customer = new Customer("Test User", "test@example.com", null, null);
        Customer saved = repository.save(customer);
        Optional<Customer> found = repository.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void testFindByEmail() {
        Customer customer = new Customer("Jane", "jane@example.com", null, null);
        repository.save(customer);
        assertTrue(repository.findByEmail("jane@example.com").isPresent());
    }
}
