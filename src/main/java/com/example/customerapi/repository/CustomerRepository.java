package com.example.customerapi.repository;

import com.example.customerapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);
}
