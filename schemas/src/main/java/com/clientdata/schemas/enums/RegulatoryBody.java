package com.clientdata.schemas.enums;

import lombok.Getter;

@Getter
public enum RegulatoryBody {

    GDPR("General Data Protection Regulation", RiskLevel.HIGH, true),
    CCPA("California Consumer Privacy Act", RiskLevel.HIGH, false),
    HIPAA("Health Insurance Portability and Accountability Act", RiskLevel.HIGH, false),
    PCI_DSS("Payment Card Industry Data Security Standard", RiskLevel.CRITICAL, true),
    SOX("Sarbanes-Oxley Act", RiskLevel.HIGH, false),
    FISMA("Federal Information Security Management Act", RiskLevel.HIGH, false),
    GLBA("Gramm-Leach-Bliley Act", RiskLevel.HIGH, false),
    FERPA("Family Educational Rights and Privacy Act", RiskLevel.MEDIUM, false),
    COPPA("Children's Online Privacy Protection Act", RiskLevel.HIGH, false),
    PIPEDA("Personal Information Protection and Electronic Documents Act", RiskLevel.MEDIUM, false),
    NIST_CSF("NIST Cybersecurity Framework", RiskLevel.MEDIUM, true),
    ISO_27001("ISO/IEC 27001 Information Security Management", RiskLevel.MEDIUM, true),
    CSA_CSTAR("Cloud Security Alliance STAR Certification", RiskLevel.MEDIUM, true),
    CSA_CSTAR_V2("CSA STAR Certification v2", RiskLevel.MEDIUM, true),
    CSA_CSTAR_V3("CSA STAR Certification v3", RiskLevel.MEDIUM, true),
    CSA_CSTAR_V4("CSA STAR Certification v4", RiskLevel.MEDIUM, true);

    private final String description;
    private final RiskLevel riskLevel;
    private final boolean globalPolicy;

    RegulatoryBody(String description, RiskLevel riskLevel, boolean globalPolicy) {
        this.description = description;
        this.riskLevel = riskLevel;
        this.globalPolicy = globalPolicy;
    }
}