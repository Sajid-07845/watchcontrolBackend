package com.watchstore.repository;

import com.watchstore.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    void deleteByCreatedAtBefore(LocalDateTime timestamp);
}
