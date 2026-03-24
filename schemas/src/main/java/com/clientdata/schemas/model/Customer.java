package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Gender;
import com.clientdata.schemas.enums.MaritalStatus;
import com.clientdata.schemas.enums.Nationality;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "Customers")
public class Customer {

    @Id
    private String id;

    private String customerId;

    private String name;

    private Date dateOfBirth;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Nationality nationality;

    private String occupation;

    private String mobileNumber;

    @Email(message = "Email should be valid")
    private String email;

    private String addressLine;

    private String city;

    private String state;

    private String country;

    private String pinCode;

    private List<String> policyIds;
}