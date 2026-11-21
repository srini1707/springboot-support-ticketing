package com.training.support;
import java.time.Instant;
public record TicketComment(Long id,Long ticketId,String authorId,String message,Instant createdAt) {}
