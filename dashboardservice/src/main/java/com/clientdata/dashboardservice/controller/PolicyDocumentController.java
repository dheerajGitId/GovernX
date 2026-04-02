package com.clientdata.dashboardservice.controller;

import com.clientdata.dashboardservice.service.DashboardStatsService;
import com.clientdata.dashboardservice.service.PolicyDocumentService;
import com.clientdata.dashboardservice.service.PolicyMetricsService;
import com.clientdata.schemas.model.PolicyDocument;
import com.clientdata.schemas.model.PolicyNameCount;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/GovernX/PolicyDocument/")
@Slf4j
@AllArgsConstructor
public class PolicyDocumentController {
    private final PolicyDocumentService policyDocumentService;
    private final PolicyMetricsService policyMetricsService;
    private final DashboardStatsService  dashboardStatsService;

    @PostMapping("allPolicyDocuments")
    public List<PolicyDocument> getAllPolicyDocuments() {
        return policyDocumentService.getAllPolicies();
    }

    @PostMapping("refresh")
    public List<PolicyDocument> refreshPolicyDocuments() {
        return policyDocumentService.refreshAllPolicyDocuments();
    }

    @GetMapping("{policyId}")
    public PolicyDocument getPolicyDocumentByPolicyId(@PathVariable String policyId) {
        return policyDocumentService.getPolicyDocumentByPolicyId(policyId);
    }

    @PostMapping("customer/{customerId}/policyIds")
    public List<String> getPolicyIdsForCustomer(@PathVariable String customerId) {
        return policyMetricsService.policyIdForCustomer(customerId);
    }

    @GetMapping("customer/{customerId}/policyCount")
    public int getPolicyCountForCustomer(@PathVariable String customerId) {
        return policyMetricsService.getCountOfPolicyIdForCustomer(customerId);
    }

    @GetMapping("policyDocumentCount")
    public int getPolicyDocumentCount() {
        return dashboardStatsService.totalNumberOfPolicyDocuments();
    }

    @PostMapping("policyName/count")
    public List<PolicyNameCount> getPolicyNameCounts() {
        return dashboardStatsService.getPolicyNameCounts();
    }


}
