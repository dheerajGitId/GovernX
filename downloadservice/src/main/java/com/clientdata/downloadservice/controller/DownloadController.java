package com.clientdata.downloadservice.controller;

import com.clientdata.downloadservice.service.PdfGeneratorService;
import com.clientdata.downloadservice.service.PdfTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/GovernX/PolicyDocument/")
public class DownloadController {
    private final PdfTemplateService  pdfTemplateService;
    private final PdfGeneratorService pdfGeneratorService;

    @PostMapping("/download/{policyId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String policyId) throws Exception {

        String html = pdfTemplateService.generateHtmlReport(policyId);

        byte[] pdf = pdfGeneratorService.generatePdfFromHtml(html);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=governx-report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
