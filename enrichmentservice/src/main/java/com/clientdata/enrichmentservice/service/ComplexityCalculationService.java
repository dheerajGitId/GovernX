package com.clientdata.enrichmentservice.service;

import com.clientdata.schemas.enums.Category;
import com.clientdata.schemas.enums.RegulatoryBody;
import com.clientdata.schemas.model.PolicyDocumentSilver;
import org.springframework.stereotype.Service;

@Service
public class ComplexityCalculationService {

    public void calculateComplexityScore(PolicyDocumentSilver policyDocumentSilver) {

        int score = 0;

        // ===== 1. Risk Level =====
        if (policyDocumentSilver.getRiskLevel() != null) {
            switch (policyDocumentSilver.getRiskLevel()) {
                case LOW -> score += 15;
                case MEDIUM -> score += 30;
                case HIGH -> score += 50;
                case CRITICAL -> score += 65;
            }
        }

        // ===== 2. Global Policy =====
        if (policyDocumentSilver.isGlobalPolicy()) {
            score += 20;
        }

        // ===== 3. Applicable Regions =====
        if (policyDocumentSilver.getApplicableRegions() != null) {
            int size = policyDocumentSilver.getApplicableRegions().size();

            if (size > 3) score += 20;
            else if (size > 1) score += 12;
            else if (size == 1) score += 5;
        }

        // ===== 4. Category (from Bronze) =====
        if (policyDocumentSilver.getBronze() != null &&
                policyDocumentSilver.getBronze().getCategory() != null) {

            Category category = policyDocumentSilver.getBronze().getCategory();

            switch (category) {
                case FINANCIAL_REGULATIONS -> score += 25;
                case DATA_PROTECTION -> score += 30;
                case HEALTH_AND_SAFETY -> score += 20;
                default -> score += 10;
            }
        }

        // ===== 5. Regulatory Body =====
        if (policyDocumentSilver.getBronze() != null &&
                policyDocumentSilver.getBronze().getRegulatoryBody() != null) {

            RegulatoryBody body = policyDocumentSilver.getBronze().getRegulatoryBody();

            switch (body) {
                case PCI_DSS, GDPR -> score += 30;
                case HIPAA -> score += 25;
                case SOX -> score += 20;
                default -> score += 10;
            }
        }

        if (score > 100) {
            score = 100;
        }

        policyDocumentSilver.setPolicyComplexityScore(score);
    }
}