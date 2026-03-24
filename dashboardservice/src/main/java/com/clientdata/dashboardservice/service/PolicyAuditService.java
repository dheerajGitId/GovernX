package com.clientdata.dashboardservice.service;

import com.clientdata.dashboardservice.exception.DashboardServiceException;
import com.clientdata.schemas.model.PolicyAudit;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PolicyAuditService {
    public final PolicyAuditRepo policyAuditRepo;

    public PolicyAudit getPolicyAudit(String id) {

        PolicyAudit audit = policyAuditRepo.findByAuditId(id);

        if (audit == null) {
            throw new DashboardServiceException("PolicyAudit not found with Audit ID " + id);
        }
        return audit;
    }

    public List<PolicyAudit> getPolicyAudits() {

        return policyAuditRepo.findAll();

    }
}
