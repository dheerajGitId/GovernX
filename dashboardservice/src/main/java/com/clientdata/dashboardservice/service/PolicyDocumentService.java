package com.clientdata.dashboardservice.service;

import com.clientdata.dashboardservice.exception.DashboardServiceException;
import com.clientdata.schemas.model.PolicyDocument;
import com.clientdata.schemas.repo.PolicyDocumentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PolicyDocumentService {
    private final PolicyDocumentRepo policyDocumentRepo;

    public List<PolicyDocument> getAllPolicies() {
        return policyDocumentRepo.findAll();
    }

    public PolicyDocument getPolicyDocumentByPolicyId(String policyId) {
        PolicyDocument policyDocument = policyDocumentRepo.getPolicyDocumentByPolicyId(policyId);

        if(policyDocument == null){
            throw new DashboardServiceException("Policy Document with "+policyId+" does not exist");
        }
        return policyDocument;
    }

    
}
