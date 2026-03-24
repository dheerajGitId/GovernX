package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.ApprovalLevel;
import com.clientdata.schemas.enums.ApprovalStatus;
import com.clientdata.schemas.enums.Approvers;
import lombok.Data;

@Data
public class ApproverView {

    private Approvers approver;

    private ApprovalStatus status;

    private String comment;

    private ApprovalLevel approvalLevel;

    public ApproverView() {
        this.status = ApprovalStatus.PENDING_REVIEW;
        this.comment = "";
        this.approvalLevel = ApprovalLevel.NOT_STARTED;
    }

}
