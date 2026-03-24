package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepo extends MongoRepository<Customer, String> {
    boolean existsByCustomerId(String customerId);

    Customer findByCustomerId(String customerId);
}
