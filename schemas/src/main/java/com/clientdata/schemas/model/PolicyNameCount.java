package com.clientdata.schemas.model;

import com.clientdata.schemas.enums.PolicyName;
import lombok.Data;

@Data
public class PolicyNameCount {

    private PolicyName policyName;

    private int count;
}
