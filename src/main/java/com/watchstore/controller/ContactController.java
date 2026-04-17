package com.watchstore.controller;

import com.watchstore.model.ContactMessage;
import com.watchstore.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "http://localhost:5173")
public class ContactController {

    @Autowired
    private ContactMessageService service;

    @PostMapping
    public ResponseEntity<ContactMessage> submitMessage(@RequestBody ContactMessage message) {
        return ResponseEntity.ok(service.saveMessage(message));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ContactMessage>> getMessages() {
        return ResponseEntity.ok(service.getAllMessages());
    }
}
