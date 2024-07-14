package com.far.ora.ForoHub.models.dto.show;

import com.far.ora.ForoHub.models.Answer;

import java.time.LocalDateTime;

public record ShowAnswer(
        Long id,
        String message,
        LocalDateTime creationDate,
        Boolean bestAnswer
) {
    public ShowAnswer(Answer answer) {
        this(answer.getId(), answer.getMessage(), answer.getCreationDate(), answer.getBestAnswer());
    }
}
