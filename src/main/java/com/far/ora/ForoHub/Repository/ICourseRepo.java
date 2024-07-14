package com.far.ora.ForoHub.Repository;

import com.far.ora.ForoHub.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ICourseRepo extends JpaRepository<Course, Long> {
    Page<Course> findById(Long id, Pageable pageable);
}
