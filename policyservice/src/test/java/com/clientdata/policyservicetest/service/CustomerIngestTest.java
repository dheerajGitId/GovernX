package com.clientdata.policyservicetest.service;

import com.clientdata.policyservice.service.CustomerIngest;
import com.clientdata.schemas.enums.Message;
import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.model.ResponseBody;
import com.clientdata.schemas.repo.CustomerDetailsRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;

import java.util.List;

import static com.clientdata.schemas.enums.Message.CUSTOMER_ALREADY_EXISTS;
import static com.clientdata.schemas.enums.Message.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerIngestTest {

    @Mock
    CustomerDetailsRepo repo;

    CustomerIngest customerIngest;

    @BeforeEach
    public void setUp() {
        repo = mock(CustomerDetailsRepo.class);
        customerIngest = new CustomerIngest(repo);
    }

    @Test
    void saveCustomerDetailsIdDoesNotExist() {

        Customer customer = new Customer();

        when(repo.existsByCustomerId(anyString())).thenReturn(false);

        ResponseBody responseBody = customerIngest.saveCustomerDetails(customer);

        verify(repo, times(1)).save(customer);
        assertEquals(SUCCESS, responseBody.getMessage());
    }

    @Test
    void saveCustomerDetailsIdExists() {

        Customer customer = new Customer();

        when(repo.existsByCustomerId(anyString())).thenReturn(true);

        ResponseBody responseBody = customerIngest.saveCustomerDetails(customer);

        verify(repo, times(0)).save(customer);
        assertEquals(CUSTOMER_ALREADY_EXISTS, responseBody.getMessage());
    }
}
