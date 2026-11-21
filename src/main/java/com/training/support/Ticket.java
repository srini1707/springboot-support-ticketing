package com.training.support;
import java.time.Instant;
public record Ticket(Long id,String requesterId,String subject,String description,String priority,String status,
 String assignedAgentId,Instant createdAt,Instant updatedAt) {}
