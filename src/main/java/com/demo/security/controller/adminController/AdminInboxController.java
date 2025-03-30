package com.demo.security.controller.adminController;

import com.demo.security.entity.Inbox;
import com.demo.security.service.InboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/inbox")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminInboxController {
    private final InboxService inboxService;

    @GetMapping
    public ResponseEntity<List<Inbox>> getInboxes() {
        return ResponseEntity.ok(inboxService.getInboxes());
    }
}
