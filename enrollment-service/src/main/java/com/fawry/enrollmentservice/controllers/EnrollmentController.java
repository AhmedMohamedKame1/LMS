package com.fawry.enrollmentservice.controllers;

import com.fawry.enrollmentservice.dtos.CreateEnrollmentDto;
import com.fawry.enrollmentservice.dtos.EnrollmentResponseDto;
import com.fawry.enrollmentservice.services.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> enroll(@RequestBody CreateEnrollmentDto dto, @RequestHeader("X-User-Id") Long studentId, @RequestHeader("X-User-Role") String role) {
        if (!role.equals("STUDENT")) {
            throw new RuntimeException("Only students can enroll in courses");
        }
        return ResponseEntity.ok(enrollmentService.enroll(dto, studentId));
    }

    @GetMapping("/my-courses")
    public ResponseEntity<List<EnrollmentResponseDto>> getMyCourses(@RequestHeader("X-User-Id") Long studentId) {
        return ResponseEntity.ok(enrollmentService.getMyCourses(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getStudentsForCourse(@PathVariable Long courseId, @RequestHeader("X-User-Role") String role) {
        if (!role.equals("INSTRUCTOR")) {
            throw new RuntimeException("Only instructors can view enrolled students");
        }
        return ResponseEntity.ok(enrollmentService.getStudentsForCourse(courseId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelEnrollment(@PathVariable Long id, @RequestHeader("X-User-Id") Long studentId) {
        enrollmentService.cancelEnrollment(id, studentId);
        return ResponseEntity.noContent().build();
    }

}
