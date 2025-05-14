package com.example.customerapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;

    private BigDecimal annualSpend;

    private LocalDate lastPurchaseDate;

    @Transient
    private String tier; // Calculated on retrieval

    // Constructors
    public Customer() {
    }

    public Customer(String name, String email, BigDecimal annualSpend, LocalDate lastPurchaseDate) {
        this.name = name;
        this.email = email;
        this.annualSpend = annualSpend;
        this.lastPurchaseDate = lastPurchaseDate;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getAnnualSpend() {
        return annualSpend;
    }

    public void setAnnualSpend(BigDecimal annualSpend) {
        this.annualSpend = annualSpend;
    }

    public LocalDate getLastPurchaseDate() {
        return lastPurchaseDate;
    }

    public void setLastPurchaseDate(LocalDate lastPurchaseDate) {
        this.lastPurchaseDate = lastPurchaseDate;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }
}
