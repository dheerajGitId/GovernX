package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Category;
import com.clientdata.schemas.enums.PolicyName;
import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.enums.RegulatoryBody;
import com.clientdata.schemas.enums.Users;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@Document(collection = "PolicyDocument")
public class PolicyDocument {

    private String policyId;
    private PolicyName policyName;
    private RegulatoryBody regulatoryBody;
    private Category category;
    private String version;
    private PolicyStatus status;
    private LocalDateTime uploadDateTime;
    private Users uploadedBy;

    private PolicyUpdateAudit auditTrail;




}
