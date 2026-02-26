package com.clientdata.policyservice.service;

import com.clientdata.policyservice.exception.PolicyServiceException;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static com.clientdata.policyservice.util.PolicyServiceConstants.POLICY_CREATED;
import static com.clientdata.schemas.enums.PolicyStatus.DRAFT;
import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
@Slf4j
public class PolicyIngestService {
    private PolicyDocumentBronzeRepo policyDocumentRepo;

    public void PolicyDocumentIngest(PolicyDocumentBronze policyDocument) {
        String id = "PPP-" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
        policyDocument.setPolicyId(id);
        policyDocument.setUploadDateTime(new Date());
        policyDocument.setStatus(DRAFT);

        PolicyUpdateAudit audit = new PolicyUpdateAudit();
        audit.setUpdatedBy(policyDocument.getUploadedBy());
        audit.setPolicyId(id);
        audit.setAuditId(UUID.randomUUID().toString());
        audit.setOldStatus(null);
        audit.setNewStatus(DRAFT);
        audit.setUpdatedDateTime(policyDocument.getUploadDateTime());
        audit.setComments(POLICY_CREATED);

        policyDocument.setAuditTrail(singletonList(audit));
        log.info("policy document ingested with id: {}", policyDocument);

        if (policyDocumentRepo.existsByPolicyId(id)) {
            throw new PolicyServiceException("Policy with id already exists: " + id);
        }
        policyDocumentRepo.save(policyDocument);


    }


}
