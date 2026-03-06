package com.clientdata.enrichmentservice.service;

import com.clientdata.enrichmentservice.exception.EnrichmentServiceException;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.clientdata.schemas.enums.PolicyStatus.DRAFT;
import static com.clientdata.schemas.enums.PolicyStatus.SENT_FOR_SILVER_PROCESSING;

@Service
@AllArgsConstructor
@Slf4j
public class SilverProcessingService {
    private final PolicyDocumentBronzeRepo policyDocumentBronzeRepo;

    private List<PolicyDocumentBronze> silverProcessing() {
        List<PolicyDocumentBronze> bronzeDocuments = policyDocumentBronzeRepo.findAll();
        List<PolicyDocumentBronze> docsToProcess = new ArrayList<>();
        for (PolicyDocumentBronze bronzeDocument : bronzeDocuments) {
            if (bronzeDocument.getStatus().equals(DRAFT)) {
                bronzeDocument.setStatus(SENT_FOR_SILVER_PROCESSING);
            }
        }
        policyDocumentBronzeRepo.saveAll(docsToProcess);
        return docsToProcess;
    }
}
