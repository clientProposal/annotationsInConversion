package com.demo.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.pdftron.common.PDFNetException;
import com.pdftron.pdf.*;

@Service 
public class ApryseConversionService {

    private static final Logger logger = LoggerFactory.getLogger(ApryseConversionService.class);
    @Value("${apryse.license.key}") 
    private String apryseLicenseKey;

    public byte[] convertPdfToOffice(byte[] pdfBytes) throws IOException {

        PDFNet.initialize(apryseLicenseKey);
        PDFNet.addResourceSearchPath(System.getProperty("user.dir") + "/lib/");
        // ********************** PART 1
        // ******************************************************************
        try {
            if (!StructuredOutputModule.isModuleAvailable()) {
                System.out.println("1. FAILURE. StructuredOutputModule not available.");
            } else {
                System.out.println("1. SUCCESS. StructuredOutputModule available.");
            }
        } catch (PDFNetException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }

        // ********************** PART 2
        // ******************************************************************
        Path inputFile = Files.createTempFile("input", ".pdf");
        Path outputFile = Files.createTempFile("output", ".docx");
        try {

            Files.write(inputFile, pdfBytes);
            PDFDoc pdfDoc = new PDFDoc(inputFile.toString());
            if (!pdfDoc.initSecurityHandler()) {
                throw new IOException("PDF is encrypted or invalid");
            }
            Convert.toWord(pdfDoc, outputFile.toString());
            return Files.readAllBytes(outputFile);
        } catch (Exception e) {
            System.out.println("2. FAILURE. Please see error.");
            System.err.println(e);
        } finally {
            Files.deleteIfExists(inputFile);
            Files.deleteIfExists(outputFile);
            System.out.println("1. SUCCESS, THEORETICALLY. Temp files being deleted.");
        }
        String mockContent = "This is a mock Word document content. Replace with actual Apryse PDFNet conversion.";
        return mockContent.getBytes();
    }
}