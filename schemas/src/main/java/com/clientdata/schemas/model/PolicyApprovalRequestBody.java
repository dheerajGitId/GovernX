package com.clientdata.schemas.model;

import lombok.Data;

@Data
public class PolicyApprovalRequestBody {
    private String policyId;
    private ApproverView approverView;
}
