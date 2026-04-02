package com.clientdata.enrichmentservice.service;

import com.clientdata.enrichmentservice.exception.EnrichmentServiceException;
import com.clientdata.enrichmentservice.publisher.KafkaPublisher;
import com.clientdata.schemas.enums.RegulatoryBody;
import com.clientdata.schemas.enums.RiskLevel;
import com.clientdata.schemas.model.PolicyAudit;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.model.PolicyDocumentSilver;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import com.clientdata.schemas.model.SilverProcessedResponseBody;
import com.clientdata.schemas.model.KafkaResponseBody;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import com.clientdata.schemas.repo.PolicyDocumentSilverRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.clientdata.schemas.enums.PolicyStatus.DRAFT;
import static com.clientdata.schemas.enums.PolicyStatus.SENT_FOR_SILVER_PROCESSING;
import static com.clientdata.schemas.enums.PolicyStatus.SENT_VIA_KAFKA_STREAM_TO_GOLD;
import static com.clientdata.schemas.enums.PolicyStatus.SILVER_PROCESSING_COMPLETED;
import static com.clientdata.schemas.enums.Users.GOVERN_X;

@Service
@AllArgsConstructor
@Slf4j
public class SilverProcessingService {
    private final ComplexityCalculationService complexityCalculationService;
    private final PolicyDocumentBronzeRepo policyDocumentBronzeRepo;
    private final PolicyDocumentSilverRepo policyDocumentSilverRepo;
    private final PolicyAuditRepo policyAuditRepo;
    private final KafkaPublisher kafkaPublisher;

    public List<PolicyDocumentBronze> bronzeDocumentProcessing() {
        List<PolicyDocumentBronze> bronzeDocuments = policyDocumentBronzeRepo.findAll();
        List<PolicyDocumentBronze> docsToProcess = new ArrayList<>();
        for (PolicyDocumentBronze bronzeDocument : bronzeDocuments) {
            if (bronzeDocument.getStatus().equals(DRAFT)) {
                updateAuditTrailForBronzeProcessing(bronzeDocument);

                bronzeDocument.setStatus(SENT_FOR_SILVER_PROCESSING);

                docsToProcess.add(bronzeDocument);
            }

        }
        policyDocumentBronzeRepo.saveAll(docsToProcess);
        log.info("Documents fetched for silver processing: {}", docsToProcess.size());
        return docsToProcess;
    }

    @Scheduled(fixedDelay = 60 * 60 * 1000) // Run once every 1 hour
    public KafkaResponseBody silverProcessing() {
        List<PolicyDocumentBronze> docsToProcess = bronzeDocumentProcessing();
        log.info("Documents sent for silver processing: {}", docsToProcess.size());

        List<PolicyDocumentSilver> silverDocuments = new ArrayList<>();
        for (PolicyDocumentBronze bronzeDocument : docsToProcess) {
            PolicyDocumentSilver silverDocument = new PolicyDocumentSilver();
            silverDocument.setBronze(bronzeDocument);
            RiskLevel riskLevel = bronzeDocument.getRegulatoryBody().getRiskLevel();
            silverDocument.setRiskLevel(riskLevel);

            RegulatoryBody regulatoryBody = bronzeDocument.getRegulatoryBody();
            silverDocument.setGlobalPolicy(regulatoryBody.isGlobalPolicy());
            processApplicableRegions(regulatoryBody, silverDocument);
            complexityCalculationService.calculateComplexityScore(silverDocument);

            silverDocument.setProcessedTimestamp(new Date());
            silverDocument.setAuditDBId(bronzeDocument.getAuditTrail_id());
            silverDocument.setStatus(SILVER_PROCESSING_COMPLETED);

            silverDocuments.add(silverDocument);
            updateAuditTrailForSilverProcessing(silverDocument);
        }
        policyDocumentSilverRepo.saveAll(silverDocuments);

        if (silverDocuments.isEmpty()) {
            log.info("No documents to process for silver processing");
            return null;
        }
        SilverProcessedResponseBody responseBody = new SilverProcessedResponseBody();
        responseBody.setId(UUID.randomUUID().toString());
        responseBody.setSilverDocuments(silverDocuments);
        for (PolicyDocumentSilver silverDocument : silverDocuments) {
            log.info("Silver Document for policyId: {}, riskLevel: {}, complexityScore: {}", silverDocument.getBronze().getPolicyId(), silverDocument.getRiskLevel(), silverDocument.isGlobalPolicy());
            updateAuditTrailForGoldProcessing(silverDocument);
        }
        return kafkaPublisher.publishSilverProcessingDocumentToGold(responseBody);
    }

    private void updateAuditTrailForBronzeProcessing(PolicyDocumentBronze bronzeDocument) {
        String policyId = bronzeDocument.getPolicyId();
        String auditDBId = bronzeDocument.getAuditTrail_id();

        log.info("policyId: {}, auditDBId: {}", policyId, auditDBId);

        PolicyAudit policyAudit = policyAuditRepo.findByPolicyIdAndAuditId(policyId, auditDBId);

        if (policyAudit == null) {

            throw new EnrichmentServiceException("No audit trail found for policyId: " + policyId + " and auditDBId: " + auditDBId);
        }
        PolicyUpdateAudit audit = new PolicyUpdateAudit();
        audit.setAuditId(UUID.randomUUID().toString());
        audit.setPolicyId(bronzeDocument.getPolicyId());
        audit.setUpdatedBy(GOVERN_X);
        audit.setOldStatus(bronzeDocument.getStatus());

        audit.setNewStatus(SENT_FOR_SILVER_PROCESSING);
        audit.setComments("Policy sent for silver processing");
        audit.setUpdatedDateTime(new java.util.Date());

        policyAudit.getPolicyUpdateAudit().add(audit);
        policyAuditRepo.save(policyAudit);

    }

    private void processApplicableRegions(RegulatoryBody regulatoryBody, PolicyDocumentSilver silverDocument) {

        if (regulatoryBody == null) {
            return;
        }

        List<String> regions = new ArrayList<>();

        switch (regulatoryBody) {

            case GDPR:
                regions.add("EU");
                break;

            case CCPA:
                regions.add("USA");
                regions.add("CALIFORNIA");
                break;

            case HIPAA, SOX, FISMA, GLBA, FERPA, COPPA:
                regions.add("USA");
                break;

            case PCI_DSS, ISO_27001, NIST_CSF:
                regions.add("GLOBAL");
                break;

            case PIPEDA:
                regions.add("CANADA");
                break;

            default:
                regions.add("OTHER");
        }

        silverDocument.setApplicableRegions(regions);
    }

    private void updateAuditTrailForSilverProcessing(PolicyDocumentSilver silverDocument) {
        String policyId = silverDocument.getBronze().getPolicyId();
        String auditDBId = silverDocument.getAuditDBId();

        log.info("policyId: {}, auditDBId: {} for silver Processing", policyId, auditDBId);

        PolicyAudit policyAudit = policyAuditRepo.findByPolicyIdAndAuditId(policyId, auditDBId);

        if (policyAudit == null) {

            throw new EnrichmentServiceException("No audit trail found for policyId: " + policyId + " and auditDBId: " + auditDBId);
        }
        PolicyUpdateAudit audit = new PolicyUpdateAudit();
        audit.setAuditId(UUID.randomUUID().toString());
        audit.setPolicyId(silverDocument.getBronze().getPolicyId());
        audit.setUpdatedBy(GOVERN_X);
        audit.setOldStatus(SENT_FOR_SILVER_PROCESSING);

        audit.setNewStatus(SILVER_PROCESSING_COMPLETED);
        audit.setComments("Policy's Silver Processing is Done");
        audit.setUpdatedDateTime(new Date());

        policyAudit.getPolicyUpdateAudit().add(audit);
        policyAuditRepo.save(policyAudit);

    }

    public void updateAuditTrailForGoldProcessing(PolicyDocumentSilver silverDocument) {
        String policyId = silverDocument.getBronze().getPolicyId();
        String auditDBId = silverDocument.getAuditDBId();

        log.info("policyId: {}, auditDBId: {} for gold Processing", policyId, auditDBId);

        PolicyAudit policyAudit = policyAuditRepo.findByPolicyIdAndAuditId(policyId, auditDBId);

        if (policyAudit == null) {

            throw new EnrichmentServiceException("No audit trail found for policyId: " + policyId + " and auditDBId: " + auditDBId);
        }
        PolicyUpdateAudit audit = new PolicyUpdateAudit();
        audit.setAuditId(UUID.randomUUID().toString());
        audit.setPolicyId(policyId);
        audit.setUpdatedBy(GOVERN_X);
        audit.setOldStatus(SILVER_PROCESSING_COMPLETED);
        audit.setNewStatus(SENT_VIA_KAFKA_STREAM_TO_GOLD);
        audit.setComments("Policy sent to gold processing via Kafka Stream");
        audit.setUpdatedDateTime(new Date());
        policyAudit.getPolicyUpdateAudit().add(audit);
        policyAuditRepo.save(policyAudit);
    }

}

