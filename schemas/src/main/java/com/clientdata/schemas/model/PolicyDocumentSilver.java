package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.PolicyStatus;
import com.clientdata.schemas.enums.RiskLevel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "PolicyDocumentSilver")
public class PolicyDocumentSilver {

    @Id
    private String id;

    private PolicyDocumentBronze bronze;

    private RiskLevel riskLevel;

    private boolean globalPolicy;

    private List<String> applicableRegions;

    private int policyComplexityScore;

    private String regulatoryCategoryCode;

    private Date processedTimestamp;

    private String auditDBId;

    private PolicyStatus status;

}
