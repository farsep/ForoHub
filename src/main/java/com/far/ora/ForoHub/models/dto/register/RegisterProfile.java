package com.far.ora.ForoHub.models.dto.register;

import jakarta.validation.constraints.NotBlank;

public record RegisterProfile(
        @NotBlank
        String username
) {
}
