package com.far.ora.ForoHub.models.dto.register;

import jakarta.validation.constraints.NotBlank;

public record RegisterCourse(
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
