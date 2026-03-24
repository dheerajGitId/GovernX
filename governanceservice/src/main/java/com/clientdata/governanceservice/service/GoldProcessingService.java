package com.clientdata.governanceservice.service;

import com.clientdata.governanceservice.exception.GovernanceServiceException;
import com.clientdata.schemas.enums.Approvers;
import com.clientdata.schemas.enums.Nationality;
import com.clientdata.schemas.enums.PolicyName;
import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.model.ApproverView;
import com.clientdata.schemas.model.PolicyAudit;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import com.clientdata.schemas.model.PolicyDocumentGold;
import com.clientdata.schemas.model.PolicyDocumentSilver;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentGoldRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.clientdata.schemas.enums.ApprovalLevel.NOT_STARTED;
import static com.clientdata.schemas.enums.ApprovalStatus.PENDING_REVIEW;
import static com.clientdata.schemas.enums.PolicyStatus.SENT_FOR_GOLD_PROCESSING;
import static com.clientdata.schemas.enums.PolicyStatus.SILVER_PROCESSING_COMPLETED;
import static com.clientdata.schemas.enums.Users.GOVERN_X;

@Service
@AllArgsConstructor
public class GoldProcessingService {
    private final PolicyDocumentGoldRepo policyDocumentGoldRepo;
    private final PolicyAuditRepo policyAuditRepo;
    private final ComplianceOfficerSelectorService selectorService;

    public void ingestSilverDocuments(List<PolicyDocumentSilver> silverDocuments) {

        List<PolicyDocumentGold> goldDocuments = new ArrayList<>();
        for (PolicyDocumentSilver silverDocument : silverDocuments) {
            if (silverDocument.getStatus().equals(SILVER_PROCESSING_COMPLETED)) {
                PolicyDocumentGold policyDocumentGold = new PolicyDocumentGold();
                policyDocumentGold.setSilver(silverDocument);

                String auditId = silverDocument.getAuditDBId();
                policyDocumentGold.setAuditDbId(auditId);

                String policyId = silverDocument.getBronze().getPolicyId();
                policyDocumentGold.setPolicyId(policyId);

                Nationality nationality = silverDocument.getBronze().getUploadedBy().getNationality();
                policyDocumentGold.setComplianceOfficer(selectorService.getRandomComplianceOfficer(nationality));

                PolicyName name = silverDocument.getBronze().getPolicyName();
                List<Approvers> approvers = name.getApprovers();

                List<ApproverView> approverViews = new ArrayList<>();

                for (Approvers approver : approvers) {
                    ApproverView approverView = new ApproverView();
                    approverView.setApprover(approver);
                    approverViews.add(approverView);
                }

                policyDocumentGold.setApproverView(approverViews);
                policyDocumentGold.setApprovalStatus(PENDING_REVIEW);
                policyDocumentGold.setOverallApprovalLevel(NOT_STARTED);
                policyDocumentGold.setApprovalStartDate(new Date());
                policyDocumentGold.setPolicyStatus(SENT_FOR_GOLD_PROCESSING);

                goldAuditUpdate(policyDocumentGold);

                goldDocuments.add(policyDocumentGold);

            }

        }
        policyDocumentGoldRepo.saveAll(goldDocuments);
    }

    private void goldAuditUpdate(PolicyDocumentGold policyDocumentGold) {
        String auditId = policyDocumentGold.getAuditDbId();
        String policyId = policyDocumentGold.getSilver().getBronze().getPolicyId();

        PolicyAudit policyAudit = policyAuditRepo.findByPolicyIdAndAuditId(policyId, auditId);
        if (policyAudit == null) {
            throw new GovernanceServiceException("No audit trail found for policyId: " + policyId + " and auditDBId: " + auditId);
        }

        PolicyStatus status = policyAudit.getPolicyUpdateAudit().getLast().getNewStatus();

        PolicyUpdateAudit audit = new PolicyUpdateAudit();
        audit.setAuditId(UUID.randomUUID().toString());
        audit.setPolicyId(policyId);
        audit.setUpdatedBy(GOVERN_X);
        audit.setOldStatus(status);

        audit.setNewStatus(SENT_FOR_GOLD_PROCESSING);
        audit.setComments("Policy sent for gold processing");
        audit.setUpdatedDateTime(new java.util.Date());

        policyAudit.getPolicyUpdateAudit().add(audit);
        policyAuditRepo.save(policyAudit);
    }
}
