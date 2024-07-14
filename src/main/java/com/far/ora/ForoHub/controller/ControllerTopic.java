package com.far.ora.ForoHub.controller;

import com.far.ora.ForoHub.Repository.ICourseRepo;
import com.far.ora.ForoHub.Repository.ITopicRepo;
import com.far.ora.ForoHub.Repository.IUserRepo;
import com.far.ora.ForoHub.models.Topic;
import com.far.ora.ForoHub.models.dto.register.RegisterTopic;
import com.far.ora.ForoHub.models.dto.show.ShowTopic;
import com.far.ora.ForoHub.models.dto.update.UpdateTopic;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topic")
public class ControllerTopic {

    @Autowired
    private ITopicRepo topicRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private ICourseRepo courseRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ShowTopic> getTopic(@PathVariable Long id) {
        Topic topic = topicRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(new ShowTopic(topic));
    }

    @GetMapping
    public ResponseEntity<Page<ShowTopic>> getAllTopics(Pageable pageable) {
        Page<ShowTopic> topics = topicRepo.findAll(pageable).map(ShowTopic::new);
        return ResponseEntity.ok(topics);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ShowTopic> registerTopic(@RequestBody RegisterTopic topic) {
        Topic topic1 = new Topic(topic);
        topic1.setUser(userRepo.findById(topic.userId()).orElseThrow());
        topic1.setCourse(courseRepo.findById(topic.courseId()).orElseThrow());
        topicRepo.save(topic1);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(topic1.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ShowTopic(topic1));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ShowTopic> updateTopic(@PathVariable Long id, @RequestBody UpdateTopic topic) {
        Topic topic1 = topicRepo.findById(id).orElseThrow();
        topic1.Update(topic);
        topicRepo.save(topic1);
        return ResponseEntity.ok(new ShowTopic(topic1));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long id) {
        topicRepo.deleteById(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Topic deleted successfully");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(jsonObject.toString());
    }
}