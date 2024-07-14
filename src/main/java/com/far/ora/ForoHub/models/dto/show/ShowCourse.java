package com.far.ora.ForoHub.models.dto.show;

import com.far.ora.ForoHub.models.Course;
import java.util.List;
import java.util.stream.Collectors;

public record ShowCourse(
        String name,
        String category,
        List<ShowTopic> topics
) {
    public ShowCourse(Course course) {
        this(course.getName(), course.getCategory(),
                course.getTopics().stream().map(ShowTopic::new).collect(Collectors.toList()));
    }
}
