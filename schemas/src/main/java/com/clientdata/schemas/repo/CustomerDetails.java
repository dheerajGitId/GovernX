package com.clientdata.schemas.repo;

import com.clientdata.schemas.model.Customer;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Document(collection = "CustomerDetails")
public class CustomerDetails {

    private String CustomerId;
    private Customer customer;

    private Timestamp customerCreationTimeStamp;
    private long age;

}
