package com.far.ora.ForoHub.models.dto.register;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterAnswer(
        @NotBlank
        String title,
        @NotBlank
        String message,
        @NotNull
        Long userId,
        @NotNull
        Long topicId
) {
}
