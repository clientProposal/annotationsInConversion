// File: backend/src/main/java/com/demo/backend/controller/FileConverterController.java
package com.demo.backend.controller;

import com.demo.backend.service.ApryseConversionService; 
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.core.io.*;
import org.springframework.beans.factory.annotation.Autowired; 
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileConverterController {

    // Inject the service
    private final ApryseConversionService apryseConversionService;

    // Constructor injection (recommended)
    @Autowired
    public FileConverterController(ApryseConversionService apryseConversionService) {
        this.apryseConversionService = apryseConversionService;
    }

    @PostMapping(path = "/convert", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> convertToOffice(@RequestParam("file") MultipartFile file) throws IOException {
        // 1. Get the input file bytes
        byte[] fileBytes = file.getBytes();

        // 2. Call the injected service to convert PDF->Office
        byte[] convertedBytes = apryseConversionService.convertPdfToOffice(fileBytes);

        // 3. Return the converted file as a download
        ByteArrayResource resource = new ByteArrayResource(convertedBytes);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"converted.docx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}