package com.demo.security.repository;

import com.demo.security.entity.Inbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InboxRepository extends JpaRepository<Inbox, UUID> {
}
