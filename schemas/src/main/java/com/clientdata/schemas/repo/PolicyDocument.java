package com.clientdata.schemas.repo;

import com.clientdata.schemas.enums.*;
import com.clientdata.schemas.model.PolicyUpdateAudit;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Document(collection = "PolicyDocument")
public class PolicyDocument {

    private String policyId;
    private PolicyName policyName;
    private RegulatoryBody regulatoryBody;
    private Category category;
    private String version;
    private PolicyStatus status;
    private Timestamp uploadDateTime;
    private Users uploadedBy;

    private PolicyUpdateAudit auditTrail;




}
