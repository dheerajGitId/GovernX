package com.clientdata.schemas.enums;

public enum RegulatoryBody {
    GDPR("General Data Protection Regulation"),
    CCPA("California Consumer Privacy Act"),
    HIPAA("Health Insurance Portability and Accountability Act"),
    PCI_DSS("Payment Card Industry Data Security Standard"),
    SOX("Sarbanes-Oxley Act"),
    FISMA("Federal Information Security Management Act"),
    GLBA("Gramm-Leach-Bliley Act"),
    FERPA("Family Educational Rights and Privacy Act"),
    COPPA("Children's Online Privacy Protection Act"),
    PIPEDA("Personal Information Protection and Electronic Documents Act"),
    NIST_CSF("NIST Cybersecurity Framework"),
    ISO_27001("ISO/IEC 27001 Information Security Management"),
    CSA_CSTAR("Cloud Security Alliance Cloud Controls Matrix STAR Certification"),
    CSA_CSTAR_V2("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 2.0"),
    CSA_CSTAR_V3("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 3.0"),
    CSA_CSTAR_V4("Cloud Security Alliance Cloud Controls Matrix STAR Certification Version 4.0");

    private final String description;

    RegulatoryBody(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
