package com.clientdata.policyservice.service;

import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.model.ResponseBody;
import com.clientdata.schemas.repo.CustomerDetailsRepo;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.clientdata.schemas.enums.Message.CUSTOMER_ALREADY_EXISTS;
import static com.clientdata.schemas.enums.Message.SUCCESS;

@Service
@AllArgsConstructor
public class CustomerIngest {
    private final CustomerDetailsRepo customerDetailsRepo;
    private final PolicyDocumentBronzeRepo policyDocumentRepo;

    public ResponseBody saveCustomerDetails(Customer customer) {
        String customerId = "CCC-" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
        customer.setCustomerId(customerId);
        ResponseBody responseBody = new ResponseBody();
        responseBody.setId(UUID.randomUUID().toString());
        responseBody.setCustomer(customer);

        if (customerDetailsRepo.existsByCustomerId(customerId)) {
            responseBody.setMessage(CUSTOMER_ALREADY_EXISTS);
            return responseBody;
        }
        customerDetailsRepo.save(customer);
        responseBody.setMessage(SUCCESS);
        return responseBody;

    }
}
