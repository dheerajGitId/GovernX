package com.clientdata.dashboardservice.controller;

import com.clientdata.dashboardservice.exception.DashboardServiceException;
import com.clientdata.dashboardservice.service.PolicyAuditService;
import com.clientdata.schemas.model.PolicyAudit;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RestController
@RequestMapping("/GovernX/Audit/")
@AllArgsConstructor
public class PolicyAuditController {
    private final PolicyAuditService policyAuditService;

    @GetMapping("{id}")
    public PolicyAudit getPolicyAudit(@PathVariable String id) {
        if (id == null) {
            throw new DashboardServiceException("Id is null");
        }
        return policyAuditService.getPolicyAudit(id);
    }

    @PostMapping("AuditHistory")
    public List<PolicyAudit> getPolicyAudits() {
        return policyAuditService.getPolicyAudits();
    }

}
