package com.clientdata.policyservice.service;

import com.clientdata.policyservice.exception.PolicyServiceException;
import com.clientdata.schemas.model.PolicyAudit;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import com.clientdata.schemas.repo.PolicyAuditRepo;
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
    private final PolicyDocumentBronzeRepo policyDocumentRepo;
    private final PolicyAuditRepo policyAuditRepo;

    public void PolicyDocumentIngest(PolicyDocumentBronze policyDocument) {
        String id = "PPP-" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
        String auditId = "AAA-" + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
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

        PolicyAudit policyAudit = new PolicyAudit();

        policyAudit.setAuditId(auditId);
        policyAudit.setPolicyId(id);
        policyAudit.setPolicyUpdateAudit(singletonList(audit));

        policyDocument.setAuditTrail_id(policyAudit.getAuditId());

        log.info("policy document ingested with id: {}", policyDocument);

        boolean policyExists = policyDocumentRepo.existsByPolicyId(id);
        boolean auditExists = policyAuditRepo.existsByAuditId(auditId);

        if (policyExists) {
            log.error("Policy already exists with policyId: {}", id);
        }

        if (auditExists) {
            log.error("Audit already exists with auditId: {}", auditId);
        }

        if (policyExists || auditExists) {
            throw new PolicyServiceException(
                    "Duplicate entry detected. policyExists=" + policyExists +
                            ", auditExists=" + auditExists
            );
        }
        policyDocumentRepo.save(policyDocument);
        policyAuditRepo.save(policyAudit);

    }


}
