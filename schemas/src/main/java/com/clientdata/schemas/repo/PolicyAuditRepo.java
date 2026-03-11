package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.PolicyAudit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyAuditRepo extends MongoRepository<PolicyAudit, String> {
    boolean existsByPolicyId(String id);

    PolicyAudit findByPolicyIdAndAuditId(String policyId, String auditId);
}
