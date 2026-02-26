package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.enums.Users;
import lombok.Data;

import java.util.Date;


@Data
public class PolicyUpdateAudit {

    private String auditId;

    private String policyId;

    private Users updatedBy;

    private PolicyStatus oldStatus;

    private PolicyStatus newStatus;

    private String comments;

    private Date updatedDateTime;
}
