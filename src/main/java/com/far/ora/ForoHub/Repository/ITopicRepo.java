package com.far.ora.ForoHub.Repository;

import com.far.ora.ForoHub.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITopicRepo extends JpaRepository<Topic, Long> {
    Page<Topic> findById(Long id, Pageable pageable);
}
