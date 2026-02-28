package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.PolicyDocumentBronze;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyDocumentBronzeRepo extends MongoRepository<PolicyDocumentBronze, String> {
        boolean existsByPolicyId(String policyId);
        PolicyDocumentBronze findByPolicyId(String policyId);
}
