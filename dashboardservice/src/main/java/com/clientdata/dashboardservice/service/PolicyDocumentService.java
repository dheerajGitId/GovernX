package com.clientdata.dashboardservice.service;

import com.clientdata.dashboardservice.configuration.TenantContext;
import com.clientdata.dashboardservice.exception.DashboardServiceException;
import com.clientdata.schemas.model.PolicyDocument;
import com.clientdata.schemas.repo.PolicyDocumentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.clientdata.dashboardservice.configuration.CacheConfig.KEY_GENERATOR;
import static com.clientdata.dashboardservice.configuration.CacheConfig.POLICY_DOCUMENT_CACHE;

@Service
@Slf4j
@AllArgsConstructor
public class PolicyDocumentService {
    private final PolicyDocumentRepo policyDocumentRepo;
    private final CacheMonitoringService cacheMonitoringService;

    @Cacheable(value = POLICY_DOCUMENT_CACHE, keyGenerator = KEY_GENERATOR)
    public List<PolicyDocument> getAllPolicies() {

        String tenantId = TenantContext.getTenant();
        log.info("Fetching policy audits from DB for tenant during caching: {}", tenantId);
        cacheMonitoringService.logPolicyAuditCacheStats();

        return policyDocumentRepo.findAll();
    }

    @CachePut(value = POLICY_DOCUMENT_CACHE, keyGenerator = KEY_GENERATOR)
    public List<PolicyDocument> refreshAllPolicyDocuments() {
        String tenantId = TenantContext.getTenant();
        log.info("Fetching policy audits from DB for tenant during refresh: {}", tenantId);
        return policyDocumentRepo.findAll();
    }

    public PolicyDocument getPolicyDocumentByPolicyId(String policyId) {
        PolicyDocument policyDocument = policyDocumentRepo.getPolicyDocumentByPolicyId(policyId);

        if (policyDocument == null) {
            throw new DashboardServiceException("Policy Document with " + policyId + " does not exist");
        }
        return policyDocument;
    }


}
