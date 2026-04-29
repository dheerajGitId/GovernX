package com.clientdata.downloadservice.service;

import com.clientdata.downloadservice.exception.DownloadServiceException;
import com.clientdata.schemas.model.PolicyDocument;
import com.clientdata.schemas.repo.PolicyDocumentRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;


@Service
@AllArgsConstructor
@Slf4j
public class PdfTemplateService {
    private final TemplateEngine templateEngine;
    private final PolicyDocumentRepo policyDocumentRepo;

    public String generateHtmlReport(String policyId){
        PolicyDocument policyDocument = policyDocumentRepo.getPolicyDocumentByPolicyId(policyId);

        if(policyDocument == null){
            throw new DownloadServiceException("Policy document not found");
        }

        Context context = new Context();
        context.setVariable("policyId", policyId);
        context.setVariable("policyName", policyDocument.getPolicyName());
        context.setVariable("category", policyDocument.getCategory());
        context.setVariable("regulatoryBody", policyDocument.getRegulatoryBody());
        context.setVariable("complianceOfficer", policyDocument.getComplianceOfficer());
        context.setVariable("approvalDate", policyDocument.getApprovalDate());
        context.setVariable("startDate", policyDocument.getStartDate());
        context.setVariable("endDate", policyDocument.getEndDate());
        context.setVariable("customerId", policyDocument.getCustomerId());
        context.setVariable("customerName", policyDocument.getCustomer().getName());
        context.setVariable("dob", policyDocument.getCustomer().getDateOfBirth());
        context.setVariable("nationality", policyDocument.getCustomer().getNationality());
        context.setVariable("email", policyDocument.getCustomer().getEmail());
        context.setVariable("location",policyDocument.getCustomer().getCountry());

        String ceoPath = new File(
                "C:/new/GovernX/downloadservice/src/main/resources/templates/CEO.png"
        ).toURI().toString();

        String logoPath = new File(
                "C:/new/GovernX/downloadservice/src/main/resources/templates/logo.png"
        ).toURI().toString();

        String spacePath = new File(
                "C:/new/GovernX/downloadservice/src/main/resources/templates/img.png"
        ).toURI().toString();

        String watermarkPath = new File(
                "C:/new/GovernX/downloadservice/src/main/resources/templates/watermark.png"
        ).toURI().toString();

        context.setVariable("CEO", ceoPath);
        context.setVariable("logo", logoPath);
        context.setVariable("space", spacePath);
        context.setVariable("watermark", watermarkPath);

        return templateEngine.process("index", context);
    }

}
