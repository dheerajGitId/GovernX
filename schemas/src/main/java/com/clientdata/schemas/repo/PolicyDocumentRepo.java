package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.PolicyDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyDocumentRepo extends MongoRepository<PolicyDocument, String> {
}
