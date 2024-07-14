package com.far.ora.ForoHub.controller;

import com.far.ora.ForoHub.Repository.ICourseRepo;
import com.far.ora.ForoHub.models.Course;
import com.far.ora.ForoHub.models.dto.register.RegisterCourse;
import com.far.ora.ForoHub.models.dto.show.ShowCourse;
import com.far.ora.ForoHub.models.dto.update.UpdateCourse;
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
@RequestMapping("/course")
public class ControllerCourse {

    @Autowired
    private ICourseRepo courseRepo;

    @GetMapping("/{id}")
    public ResponseEntity<ShowCourse> getCourse(@PathVariable Long id) {
        Course course = courseRepo.findById(id).orElseThrow();
        return ResponseEntity.ok(new ShowCourse(course));
    }

    @GetMapping
    public ResponseEntity<Page<ShowCourse>> getAllCourses(Pageable pageable) {
        Page<ShowCourse> courses = courseRepo.findAll(pageable).map(ShowCourse::new);
        return ResponseEntity.ok(courses);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ShowCourse> registerCourse(@RequestBody RegisterCourse course) {
        Course course1 = new Course(course);
        courseRepo.save(course1);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course1.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ShowCourse(course1));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ShowCourse> updateCourse(@PathVariable Long id, @RequestBody UpdateCourse course) {
        Course course1 = courseRepo.findById(id).orElseThrow();
        course1.Update(course);
        courseRepo.save(course1);
        return ResponseEntity.ok(new ShowCourse(course1));
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id) {
        courseRepo.deleteById(id);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Course deleted successfully");
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(header).body(jsonObject.toString());
    }
}