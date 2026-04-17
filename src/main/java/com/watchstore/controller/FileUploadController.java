package com.watchstore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    private final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @PostMapping
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "File is empty"));
        }

        try {
            // Create directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generate unique filename
            String fileExtension = getFileExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(fileName);

            // Copy file to directory
            Files.copy(file.getInputStream(), filePath);

            // Return the URL (assuming backend is on 8081)
            String fileUrl = "http://localhost:8081/uploads/" + fileName;
            return ResponseEntity.ok(Collections.singletonMap("url", fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", "Could not upload file: " + e.getMessage()));
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return ".jpg"; // Default
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
