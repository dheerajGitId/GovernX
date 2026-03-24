package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.PolicyName;
import com.clientdata.schemas.enums.RiskLevel;
import com.clientdata.schemas.enums.RegulatoryBody;
import com.clientdata.schemas.enums.Category;
import com.clientdata.schemas.enums.Users;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("PolicyDocument")
public class PolicyDocument {

    @Id
    private String id;

    private String policyId;

    private String customerId;

    private Customer customer;

    private PolicyName policyName;

    private RegulatoryBody regulatoryBody;

    private Category category;

    private RiskLevel riskLevel;

    private boolean globalPolicy;

    private int policyComplexityScore;

    private Users complianceOfficer;

    private Date approvalDate;

    private Date startDate;

    private Date endDate;

}
