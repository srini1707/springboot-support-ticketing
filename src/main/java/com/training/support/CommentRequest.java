package com.training.support;
import jakarta.validation.constraints.*;
public record CommentRequest(@NotBlank String authorId,@NotBlank @Size(max=2000) String message) {}
