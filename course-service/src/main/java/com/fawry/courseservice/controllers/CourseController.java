package com.fawry.courseservice.controllers;

import com.fawry.courseservice.dtos.CourseResponseDto;
import com.fawry.courseservice.dtos.CreateCourseDto;
import com.fawry.courseservice.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CreateCourseDto dto, @RequestHeader("X-User-Id") Long userId,  @RequestHeader("X-User-Role") String role) {
        return ResponseEntity.ok(courseService.createCourse(dto, userId, role));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getCourseById(id));
    }
}
