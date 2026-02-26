package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Category;
import com.clientdata.schemas.enums.PolicyName;
import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.enums.RegulatoryBody;
import com.clientdata.schemas.enums.Users;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "PolicyDocumentBronze")
public class PolicyDocumentBronze {

    private String policyId;

    private PolicyName policyName;

    private RegulatoryBody regulatoryBody;

    private Category category;

    private String version;

    private PolicyStatus status;

    private Date uploadDateTime;

    private Users uploadedBy;

    private List<PolicyUpdateAudit> auditTrail;

    private String customerId;

}
