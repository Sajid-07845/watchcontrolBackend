package com.watchstore.service;

import com.watchstore.model.ContactMessage;
import java.util.List;

public interface ContactMessageService {
    ContactMessage saveMessage(ContactMessage message);
    List<ContactMessage> getAllMessages();
    void cleanupOldMessages();
}
