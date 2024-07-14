package com.far.ora.ForoHub.models;

import com.far.ora.ForoHub.models.dto.register.RegisterTopic;
import com.far.ora.ForoHub.models.dto.update.UpdateTopic;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Entity
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;
    private String message;
    private LocalDateTime creationDate;
    private Boolean Solved;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "topic")
    private List<Answer> answers;

    public Topic(RegisterTopic topic) {
        this.title = topic.title();
        this.message = topic.message();
        this.creationDate = LocalDateTime.now();
        this.Solved = false;
    }

    public void Update(UpdateTopic topic) {
        if (topic.title() != null && !topic.title().isBlank()) {
            this.title = topic.title();
        }
        if (topic.message() != null && !topic.message().isBlank()) {
            this.message = topic.message();
        }
        if (topic.Solved() != null) {
            this.Solved = topic.Solved();
        }
    }
}
