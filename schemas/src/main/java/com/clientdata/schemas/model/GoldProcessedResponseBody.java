package com.clientdata.schemas.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class GoldProcessedResponseBody {

    @Id
    private String id;

    private PolicyDocumentGold policyDocumentGold;

    private Date timestamp;
}
