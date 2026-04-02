package com.clientdata.persistenceservice.service;

import com.clientdata.persistenceservice.exception.PersistenceServiceException;
import com.clientdata.schemas.model.*;
import com.clientdata.schemas.repo.CustomerDetailsRepo;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static com.clientdata.schemas.enums.PolicyStatus.RECEIVED_AT_PLATINUM;
import static com.clientdata.schemas.enums.Users.GOVERN_X;
import static java.util.Collections.singletonList;

@Service
@Slf4j
@AllArgsConstructor
public class PlatinumPolicyIngestion {
    private final PolicyAuditRepo policyAuditRepo;
    private final CustomerDetailsRepo customerDetailsRepo;
    private final PolicyDocumentRepo policyDocumentRepo;


    public void policyIngestion(GoldProcessedResponseBody message) {

        String policyId = message.getPolicyDocumentGold().getPolicyId();
        log.info("Policy ingestion started. PolicyId: {}", policyId);

        messageReceivedViaKafkaForSaving(message);

        PolicyDocument policyDocument = new PolicyDocument();
        PolicyDocumentGold policyDocumentGold = message.getPolicyDocumentGold();

        policyDocument.setPolicyId(policyId);
        policyDocument.setCustomerId(policyDocumentGold.getSilver().getBronze().getCustomerId());

        log.info("Fetching customer. CustomerId: {}", policyDocument.getCustomerId());

        Customer customer = customerDetailsRepo.findByCustomerId(policyDocument.getCustomerId());
        if (customer == null) {
            log.info("Customer not found. CustomerId: {}", policyDocument.getCustomerId());
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

        try {
            policyDocumentRepo.save(policyDocument);
            log.info("Policy saved successfully. PolicyId: {}", policyId);
        } catch (Exception e) {
            log.info("Error while saving policy. PolicyId: {}, Error: {}", policyId, e.getMessage());
            throw new PersistenceServiceException("Error saving policy document");
        }

        customer.setPolicyIds(singletonList(policyDocument.getPolicyId()));
        customerDetailsRepo.save(customer);

        log.info("Customer updated with policy. CustomerId: {}, PolicyId: {}",
                customer.getCustomerId(), policyId);

        log.info("Policy ingestion completed. PolicyId: {}", policyId);
    }

    private void messageReceivedViaKafkaForSaving(GoldProcessedResponseBody message) {

        String auditId = message.getPolicyDocumentGold().getAuditDbId();
        String policyId = message.getPolicyDocumentGold().getPolicyId();

        log.info("Audit update started. AuditId: {}, PolicyId: {}", auditId, policyId);

        PolicyAudit policyAudit = policyAuditRepo.findByAuditId(auditId);

        if (policyAudit == null) {
            log.info("Policy audit not found. AuditId: {}", auditId);
            throw new PersistenceServiceException("Policy Audit not found");
        }

        PolicyUpdateAudit policyUpdateAudit = getPolicyUpdateAudit(policyId, auditId, policyAudit);

        policyAudit.getPolicyUpdateAudit().add(policyUpdateAudit);
        policyAuditRepo.save(policyAudit);

        log.info("Audit updated successfully. AuditId: {}", auditId);
    }

    private PolicyUpdateAudit getPolicyUpdateAudit(String policyId, String auditId, PolicyAudit policyAudit) {

        log.info("Creating audit entry. PolicyId: {}, AuditId: {}", policyId, auditId);

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

        log.info("End date set. PolicyId: {}, EndDate: {}",
                policyDocument.getPolicyId(), endDate);
    }
}
