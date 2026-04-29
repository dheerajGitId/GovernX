package com.clientdata.governanceservice.service;

import com.clientdata.schemas.repo.PolicyDocumentGoldRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class GoldProcessingDashBoardService {
    private final PolicyDocumentGoldRepo policyDocumentGoldRepo;

}
