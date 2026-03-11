package com.clientdata.schemas.enums;

import lombok.Getter;

@Getter
public enum RegulatoryBody {

    GDPR("General Data Protection Regulation", RiskLevel.HIGH),
    CCPA("California Consumer Privacy Act", RiskLevel.HIGH),
    HIPAA("Health Insurance Portability and Accountability Act", RiskLevel.HIGH),
    PCI_DSS("Payment Card Industry Data Security Standard", RiskLevel.CRITICAL),
    SOX("Sarbanes-Oxley Act", RiskLevel.HIGH),
    FISMA("Federal Information Security Management Act", RiskLevel.HIGH),
    GLBA("Gramm-Leach-Bliley Act", RiskLevel.HIGH),
    FERPA("Family Educational Rights and Privacy Act", RiskLevel.MEDIUM),
    COPPA("Children's Online Privacy Protection Act", RiskLevel.HIGH),
    PIPEDA("Personal Information Protection and Electronic Documents Act", RiskLevel.MEDIUM),
    NIST_CSF("NIST Cybersecurity Framework", RiskLevel.MEDIUM),
    ISO_27001("ISO/IEC 27001 Information Security Management", RiskLevel.MEDIUM),
    CSA_CSTAR("Cloud Security Alliance Cloud Controls Matrix STAR Certification", RiskLevel.MEDIUM),
    CSA_CSTAR_V2("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 2.0", RiskLevel.MEDIUM),
    CSA_CSTAR_V3("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 3.0", RiskLevel.MEDIUM),
    CSA_CSTAR_V4("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 4.0", RiskLevel.MEDIUM);

    private final String description;
    private final RiskLevel riskLevel;

    RegulatoryBody(String description, RiskLevel riskLevel) {
        this.description = description;
        this.riskLevel = riskLevel;
    }

}