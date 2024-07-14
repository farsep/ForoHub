package com.far.ora.ForoHub.Repository;

import com.far.ora.ForoHub.models.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepo extends JpaRepository<Answer, Long> {
    Page<Answer> findById(Long id, Pageable pageable);
}
