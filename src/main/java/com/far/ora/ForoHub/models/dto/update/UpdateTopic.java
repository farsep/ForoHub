package com.far.ora.ForoHub.models.dto.update;

import java.time.LocalDateTime;

public record UpdateTopic(
        String title,
        String message,
        Boolean Solved
) {
}
