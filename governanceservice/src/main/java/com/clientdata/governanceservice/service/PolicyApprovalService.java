package com.clientdata.governanceservice.service;

import com.clientdata.governanceservice.exception.GovernanceServiceException;
import com.clientdata.governanceservice.producer.KafkaPublisher;
import com.clientdata.schemas.enums.Approvers;
import com.clientdata.schemas.model.ApproverView;
import com.clientdata.schemas.model.PolicyDocumentGold;
import com.clientdata.schemas.model.GoldProcessedResponseBody;
import com.clientdata.schemas.model.PolicyAudit;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import com.clientdata.schemas.repo.PolicyAuditRepo;
import com.clientdata.schemas.repo.PolicyDocumentGoldRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.clientdata.schemas.enums.ApprovalLevel.COMPLETED;
import static com.clientdata.schemas.enums.ApprovalLevel.IN_PROGRESS;
import static com.clientdata.schemas.enums.ApprovalStatus.APPROVED;
import static com.clientdata.schemas.enums.PolicyStatus.APPROVER_ACCEPTANCE_RECEIVED;
import static com.clientdata.schemas.enums.Users.GOVERN_X;

@Service
@AllArgsConstructor
@Slf4j
public class PolicyApprovalService {
    private final PolicyDocumentGoldRepo policyDocumentGoldRepo;
    private final KafkaPublisher kafkaPublisher;
    private final PolicyAuditRepo policyAuditRepo;

    public List<ApproverView> policyApproval(String policyId, ApproverView view) throws GovernanceServiceException {

        PolicyDocumentGold policyDocumentGold = policyDocumentGoldRepo.getPolicyDocumentGoldByPolicyId(policyId);
        if (policyDocumentGold == null) {
            throw new GovernanceServiceException("Policy Document Gold Not Found");
        }


        List<ApproverView> approverViews = policyDocumentGold.getApproverView();

        boolean approverExistence = approverViews.stream().anyMatch(a -> a.getApprover().equals(view.getApprover()));
        if (approverExistence) {
            return updatePolicyDocumentStatus(policyDocumentGold, view);
        }

        throw new GovernanceServiceException("Approver Not Found for this Policy");

    }

    private List<ApproverView> updatePolicyDocumentStatus(PolicyDocumentGold policyDocumentGold, ApproverView approverView) {
        List<ApproverView> approverViews = policyDocumentGold.getApproverView();
        boolean flag = false;
        for (ApproverView view : approverViews) {
            if (view.getApprover().equals(approverView.getApprover())) {
                if (view.getStatus().equals(approverView.getStatus())) {
                    log.error("Status cannot be changed as the received status is same");
                }
                view.setStatus(approverView.getStatus());
                view.setComment(approverView.getComment());
                view.setApprovalLevel(approverView.getApprovalLevel());
                publishedForPlatinumAuditForApproverUpdate(policyDocumentGold, view.getApprover());
                policyDocumentGold.setOverallApprovalLevel(IN_PROGRESS);

                flag = true;
            }


        }
        if (flag) {
            if (checkIfAllApproversStatusIsApproved(policyDocumentGold)) {
                policyDocumentGold.setOverallApprovalLevel(COMPLETED);
                processToPlatinum(policyDocumentGold);
            }
            policyDocumentGoldRepo.save(policyDocumentGold);
        } else {
            throw new GovernanceServiceException("Unable to update Policy Document");
        }


        return policyDocumentGold.getApproverView();
    }

    private boolean checkIfAllApproversStatusIsApproved(PolicyDocumentGold documentGold) {
        List<ApproverView> approverViews = documentGold.getApproverView();
        for (ApproverView view : approverViews) {
            if (!view.getStatus().equals(APPROVED)) {
                log.info("Still approvals are required");
                return false;
            }
        }
        return true;

    }

    private void processToPlatinum(PolicyDocumentGold documentGold) {
        GoldProcessedResponseBody goldProcessedResponseBody = new GoldProcessedResponseBody();
        goldProcessedResponseBody.setId(UUID.randomUUID().toString());
        goldProcessedResponseBody.setPolicyDocumentGold(documentGold);
        goldProcessedResponseBody.setTimestamp(new Date());
        kafkaPublisher.publishGoldToPlatinum(goldProcessedResponseBody);
    }

    private void publishedForPlatinumAuditForApproverUpdate(PolicyDocumentGold documentGold, Approvers approver) {
        String policyId = documentGold.getSilver().getBronze().getPolicyId();
        String auditId = documentGold.getAuditDbId();
        PolicyAudit policyAudit = policyAuditRepo.findByPolicyIdAndAuditId(policyId, auditId);

        if (policyAudit == null) {
            throw new GovernanceServiceException("Policy Audit Not Found");
        }

        PolicyUpdateAudit audit = new PolicyUpdateAudit();

        audit.setPolicyId(policyId);
        audit.setAuditId(auditId);
        audit.setUpdatedBy(GOVERN_X);
        audit.setOldStatus(policyAudit.getPolicyUpdateAudit().getLast().getOldStatus());
        audit.setNewStatus(APPROVER_ACCEPTANCE_RECEIVED);
        audit.setComments(approver.name() + " processed the request");
        audit.setUpdatedDateTime(new Date());

        policyAudit.getPolicyUpdateAudit().add(audit);

        policyAuditRepo.save(policyAudit);

    }
}
