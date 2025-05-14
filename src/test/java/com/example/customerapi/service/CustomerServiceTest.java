package com.example.customerapi.service;

import com.example.customerapi.model.Customer;
import com.example.customerapi.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock private CustomerRepository repository;
    @InjectMocks private CustomerService service;

    @BeforeEach void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCustomerById_found() {
        UUID id = UUID.randomUUID();
        Customer customer = new Customer();
        customer.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(customer));
        Customer result = service.getCustomerById(id);

        assertEquals(id, result.getId());
    }

    @Test
    void testGetCustomerById_notFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getCustomerById(id));
    }
}
