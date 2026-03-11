package com.clientdata.schemas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document()
public class PolicyAudit {

    @Id
    private String id;

    private String policyId;

    @Field("AuditDBId")
    private String auditId;

    private List<PolicyUpdateAudit> policyUpdateAudit;


}
