package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.ApprovalLevel;
import com.clientdata.schemas.enums.ApprovalStatus;
import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.enums.Users;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document("PolicyDocumentGold")
public class PolicyDocumentGold {

    @Id
    private String id;

    private PolicyDocumentSilver silver;

    private ApprovalStatus approvalStatus;

    private Users complianceOfficer;

    private List<ApproverView> approverView;

    private ApprovalLevel overallApprovalLevel;

    private Date approvalStartDate;

    private Date approvalCompletedDate;

    private Date policyActivationDate;

    private PolicyStatus policyStatus;

    private String AuditDbId;

    private String policyId;
}

