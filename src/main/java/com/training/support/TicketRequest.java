package com.training.support;
import jakarta.validation.constraints.*;
public record TicketRequest(@NotBlank String requesterId,@NotBlank @Size(max=160) String subject,
 @NotBlank @Size(max=5000) String description,@NotBlank String priority) {}
