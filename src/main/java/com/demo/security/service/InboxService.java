package com.demo.security.service;

import com.demo.security.dto.inbox.InboxCreateDto;
import com.demo.security.entity.Inbox;
import com.demo.security.repository.InboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InboxService {
    private final InboxRepository inboxRepository;

    public Inbox createInbox(InboxCreateDto inbox) {
        Inbox createdInbox = new Inbox(
                UUID.randomUUID(),
                inbox.getFullName(),
                inbox.getDescription(),
                inbox.getEmail(),
                null
        );
        return inboxRepository.save(createdInbox);
    }

    public List<Inbox> getInboxes() {
        return inboxRepository.findAll();
    }
}
