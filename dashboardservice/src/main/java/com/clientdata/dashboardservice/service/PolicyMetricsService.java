package com.clientdata.dashboardservice.service;

import com.clientdata.dashboardservice.exception.DashboardServiceException;
import com.clientdata.schemas.model.Customer;
import com.clientdata.schemas.repo.CustomerDetailsRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PolicyMetricsService {
    private final CustomerDetailsRepo customerDetailsRepo;

    public List<String> policyIdForCustomer(String customerId) {

        Customer customer = customerDetailsRepo.findByCustomerId(customerId);
        if (customer == null) {
            throw new DashboardServiceException("Customer not found");
        }
        return customer.getPolicyIds();
    }

    public int getCountOfPolicyIdForCustomer(String customerId) {
        return policyIdForCustomer(customerId).size();
    }

}
