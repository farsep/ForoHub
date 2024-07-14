package com.far.ora.ForoHub.models.dto.show;

import com.far.ora.ForoHub.models.Topic;

import java.time.LocalDateTime;
import java.util.List;

public record ShowTopic(
        Long id,
        String title,
        String message,
        LocalDateTime creationDate,
        Boolean solved,
        ShowUser user,
        List<ShowAnswer> answers
) {
    public ShowTopic(Topic topic) {
        this(topic.getId(), topic.getTitle(), topic.getMessage(), topic.getCreationDate(), topic.getSolved(),
                new ShowUser(topic.getUser()), topic.getAnswers().stream().map(ShowAnswer::new).toList());
    }
}
