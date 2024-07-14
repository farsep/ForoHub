package com.far.ora.ForoHub.models.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record RegisterTopic(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        LocalDateTime creationDate,
        @NotNull
        Boolean solved,
        @NotNull
        Long userId,
        @NotNull
        Long courseId
) {
}
