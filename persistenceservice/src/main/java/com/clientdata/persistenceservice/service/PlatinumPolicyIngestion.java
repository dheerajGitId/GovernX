package com.clientdata.persistenceservice.service;

import com.clientdata.persistenceservice.exception.PersistenceServiceException;
import com.clientdata.schemas.model.*;
import com.clientdata.schemas.repo.CustomerDetailsRepo;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.clientdata.schemas.enums.PolicyStatus.RECEIVED_AT_PLATINUM;
import static com.clientdata.schemas.enums.Users.GOVERN_X;

@Service
@AllArgsConstructor
public class PlatinumPolicyIngestion {
    private final PolicyAuditRepo policyAuditRepo;
    private final CustomerDetailsRepo customerDetailsRepo;
    private final PolicyDocumentRepo policyDocumentRepo;


    public void policyIngestion(GoldProcessedResponseBody message) {
        messageReceivedViaKafkaForSaving(message);

        PolicyDocument policyDocument = new PolicyDocument();
        PolicyDocumentGold policyDocumentGold = message.getPolicyDocumentGold();

        policyDocument.setPolicyId(policyDocumentGold.getPolicyId());
        policyDocument.setCustomerId(policyDocumentGold.getSilver().getBronze().getCustomerId());

        Customer customer = customerDetailsRepo.findByCustomerId(policyDocument.getCustomerId());
        if (customer == null) {
            throw new PersistenceServiceException("Customer not found");
        }
        policyDocument.setCustomer(customer);

        policyDocument.setPolicyName(policyDocumentGold.getSilver().getBronze().getPolicyName());
        policyDocument.setRegulatoryBody(policyDocumentGold.getSilver().getBronze().getRegulatoryBody());
        policyDocument.setCategory(policyDocumentGold.getSilver().getBronze().getCategory());
        policyDocument.setPolicyComplexityScore(policyDocumentGold.getSilver().getPolicyComplexityScore());

        policyDocument.setRiskLevel(policyDocumentGold.getSilver().getRiskLevel());
        policyDocument.setGlobalPolicy(policyDocumentGold.getSilver().isGlobalPolicy());

        policyDocument.setComplianceOfficer(policyDocumentGold.getComplianceOfficer());

        policyDocument.setApprovalDate(message.getTimestamp());

        policyDocument.setStartDate(new Date());
        setEndDate(policyDocument);

        policyDocumentRepo.save(policyDocument);

    }

    private void messageReceivedViaKafkaForSaving(GoldProcessedResponseBody message) {

        String auditId = message.getPolicyDocumentGold().getAuditDbId();
        String policyId = message.getPolicyDocumentGold().getPolicyId();

        PolicyAudit policyAudit = policyAuditRepo.findByAuditId(auditId);

        if (policyAudit == null) {
            throw new PersistenceServiceException("Policy Audit not found");
        }

        PolicyUpdateAudit policyUpdateAudit = getPolicyUpdateAudit(policyId, auditId, policyAudit);

        policyAudit.getPolicyUpdateAudit().add(policyUpdateAudit);
        policyAuditRepo.save(policyAudit);

    }

    private PolicyUpdateAudit getPolicyUpdateAudit(String policyId, String auditId, PolicyAudit policyAudit) {
        PolicyUpdateAudit policyUpdateAudit = new PolicyUpdateAudit();

        policyUpdateAudit.setPolicyId(policyId);
        policyUpdateAudit.setAuditId(auditId);
        policyUpdateAudit.setUpdatedBy(GOVERN_X);
        policyUpdateAudit.setOldStatus(policyAudit.getPolicyUpdateAudit().getLast().getNewStatus());
        policyUpdateAudit.setNewStatus(RECEIVED_AT_PLATINUM);
        policyUpdateAudit.setComments("Received at platinum");
        policyUpdateAudit.setUpdatedDateTime(new Date());
        return policyUpdateAudit;
    }

    private void setEndDate(PolicyDocument policyDocument) {

        Date startDate = policyDocument.getStartDate();
        LocalDate localStartDate = startDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        LocalDate localEndDate = localStartDate.plusMonths(12);

        Date endDate = Date.from(localEndDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());

        policyDocument.setEndDate(endDate);
    }
}
