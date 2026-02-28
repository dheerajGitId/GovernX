package com.clientdata.enrichmentservice.service;

import com.clientdata.enrichmentservice.exception.EnrichmentServiceException;
import com.clientdata.schemas.model.PolicyDocumentBronze;
import com.clientdata.schemas.repo.PolicyDocumentBronzeRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class SilverProcessingService {
    private final PolicyDocumentBronzeRepo policyDocumentBronzeRepo;

    private PolicyDocumentBronze findById(String id) {
        return policyDocumentBronzeRepo.findById(id).
    orElseThrow(() -> new EnrichmentServiceException("Policy document not found for id: " + id));
    }
}
