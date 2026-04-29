package com.clientdata.downloadservice.service;

import com.clientdata.downloadservice.exception.DownloadServiceException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Service
public class PdfGeneratorService {

    public byte[] generatePdfFromHtml(String html) throws DownloadServiceException, IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();

        builder.useFastMode();
        builder.withHtmlContent(
                html,
                new File("src/main/resources/templates/")
                        .toURI()
                        .toString()
        );

        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }
}
