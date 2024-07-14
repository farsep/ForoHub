package com.far.ora.ForoHub.models;

import com.far.ora.ForoHub.models.dto.register.RegisterAnswer;
import com.far.ora.ForoHub.models.dto.update.UpdateAnswer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "answers")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String message;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private LocalDateTime creationDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private Boolean bestAnswer;

    public Answer(RegisterAnswer answer) {
        this.message = answer.message();
        this.creationDate = LocalDateTime.now();
        this.bestAnswer = false;
    }

    public void Update(UpdateAnswer answer) {
        if (answer.message() != null && !answer.message().isBlank()) {
            this.message = answer.message();
        }
        if (answer.bestAnswer() != null) {
            this.bestAnswer = answer.bestAnswer();
        }
    }
}
