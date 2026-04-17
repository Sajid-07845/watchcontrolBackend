package com.watchstore.service.impl;

import com.watchstore.model.ContactMessage;
import com.watchstore.repository.ContactMessageRepository;
import com.watchstore.service.ContactMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ContactMessageServiceImpl implements ContactMessageService {

    @Autowired
    private ContactMessageRepository repository;

    @Override
    public ContactMessage saveMessage(ContactMessage message) {
        return repository.save(message);
    }

    @Override
    public List<ContactMessage> getAllMessages() {
        return repository.findAll();
    }

    @Override
    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanupOldMessages() {
        LocalDateTime cutoff = LocalDateTime.now().minusHours(24);
        repository.deleteByCreatedAtBefore(cutoff);
        System.out.println("--- SCHEDULED CLEANUP: Deleted contact messages older than 24 hours ---");
    }
}
