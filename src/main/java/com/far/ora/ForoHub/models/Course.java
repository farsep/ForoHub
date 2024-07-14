package com.far.ora.ForoHub.models;

import com.far.ora.ForoHub.models.dto.register.RegisterCourse;
import com.far.ora.ForoHub.models.dto.update.UpdateCourse;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String category;
    @OneToMany(mappedBy = "course")
    private List<Topic> topics;

    public Course(RegisterCourse registerCourse) {
        this.name = registerCourse.name();
        this.category = registerCourse.category();
    }

    public void Update(UpdateCourse course) {
        if (course.name() != null && !course.name().isBlank()) {
            this.name = course.name();
        }
        if (course.category() != null && !course.category().isBlank()) {
            this.category = course.category();
        }
    }
}


