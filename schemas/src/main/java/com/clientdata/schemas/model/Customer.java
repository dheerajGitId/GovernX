package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.Gender;
import com.clientdata.schemas.enums.MaritalStatus;
import com.clientdata.schemas.enums.Nationality;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "customers")
public class Customer {

    private String customerId;

    private String name;

    private Date dateOfBirth;

    private Gender gender;

    private MaritalStatus maritalStatus;

    private Nationality nationality;

    private String occupation;

    private String mobileNumber;

    private String email;

    private String addressLine;

    private String city;

    private String state;

    private String country;

    private String pinCode;

}