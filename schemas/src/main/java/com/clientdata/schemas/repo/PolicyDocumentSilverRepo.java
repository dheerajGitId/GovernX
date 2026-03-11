package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.PolicyDocumentSilver;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PolicyDocumentSilverRepo extends MongoRepository<PolicyDocumentSilver, String> {

}
