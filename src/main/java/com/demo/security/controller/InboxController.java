package com.demo.security.controller;

import com.demo.security.dto.inbox.InboxCreateDto;
import com.demo.security.entity.Inbox;
import com.demo.security.service.InboxService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public/inbox")
@RequiredArgsConstructor
public class InboxController {
    private final InboxService inboxService;

    @PostMapping
    public ResponseEntity<Inbox> createInbox(@Valid @RequestBody InboxCreateDto inbox) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inboxService.createInbox(inbox));
    }
}
