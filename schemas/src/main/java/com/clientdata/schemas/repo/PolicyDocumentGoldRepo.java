package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.PolicyDocumentGold;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyDocumentGoldRepo extends MongoRepository<PolicyDocumentGold, String> {

    PolicyDocumentGold getPolicyDocumentGoldByPolicyId(String PolicyId);
}
