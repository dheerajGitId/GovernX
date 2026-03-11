package com.clientdata.schemas.model;

import lombok.Data;

import java.util.List;

@Data
public class SilverProcessedResponseBody {

    private String id;

    private List<PolicyDocumentSilver> silverDocuments;


}
