package com.far.ora.ForoHub.controller;

import com.far.ora.ForoHub.Repository.IAnswerRepo;
import com.far.ora.ForoHub.Repository.ITopicRepo;
import com.far.ora.ForoHub.Repository.IUserRepo;
import com.far.ora.ForoHub.models.Answer;
import com.far.ora.ForoHub.models.dto.register.RegisterAnswer;
import com.far.ora.ForoHub.models.dto.show.ShowAnswer;
import com.far.ora.ForoHub.models.dto.update.UpdateAnswer;
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
@RequestMapping("/answer")
public class ControllerAnswer {

    @Autowired
    private IAnswerRepo answerRepo;

    @Autowired
    private IUserRepo userRepo;

    @Autowired
    private ITopicRepo topicRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ShowAnswer> getAnswer(@PathVariable Long id) {
        Answer answer = answerRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(new ShowAnswer(answer));
    }

    @GetMapping
    public ResponseEntity<Page<ShowAnswer>> getAllAnswers(Pageable pageable) {
        Page<ShowAnswer> answers = answerRepo.findAll(pageable).map(ShowAnswer::new);
        return ResponseEntity.ok(answers);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ShowAnswer> registerAnswer(@RequestBody RegisterAnswer answer) {
        Answer answer1 = new Answer(answer);
        answer1.setUser(userRepo.findById(answer.userId()).orElseThrow());
        answer1.setTopic(topicRepo.findById(answer.topicId()).orElseThrow());
        answerRepo.save(answer1);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(answer1.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ShowAnswer(answer1));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ShowAnswer> updateAnswer(@PathVariable Long id, @RequestBody UpdateAnswer answer) {
        Answer answer1 = answerRepo.findById(id).orElseThrow();
        answer1.Update(answer);
        answerRepo.save(answer1);
        return ResponseEntity.ok(new ShowAnswer(answer1));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnswer(@PathVariable Long id) {
        answerRepo.deleteById(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Answer deleted successfully");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(jsonObject.toString());
    }
}