package com.fawry.enrollmentservice.services;

import com.fawry.enrollmentservice.dtos.CourseDto;
import com.fawry.enrollmentservice.dtos.CreateEnrollmentDto;
import com.fawry.enrollmentservice.dtos.EnrollmentResponseDto;
import com.fawry.enrollmentservice.entities.Enrollment;
import com.fawry.enrollmentservice.entities.EnrollmentStatus;
import com.fawry.enrollmentservice.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final WebClient courseServiceWebClient;

    public EnrollmentResponseDto enroll(CreateEnrollmentDto dto, Long studentId){
        checkCourseExists(dto.getCourseId());

        if(enrollmentRepository.existsByStudentIdAndCourseId(studentId, dto.getCourseId())){
            throw new RuntimeException("Already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourseId(dto.getCourseId());
        enrollment.setEnrolledAt(new Date());
        enrollment.setStudentId(studentId);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        enrollmentRepository.save(enrollment);

        return toDto(enrollment);
    }

    public List<EnrollmentResponseDto> getMyCourses(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId).stream()
                .map(this::toDto)
                .toList();
    }

    public List<EnrollmentResponseDto> getStudentsForCourse(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId).stream()
                .map(this::toDto)
                .toList();
    }

    public void cancelEnrollment(Long enrollmentId, Long studentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (!enrollment.getStudentId().equals(studentId)) {
            throw new RuntimeException("You can only cancel your own enrollment");
        }

        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
    }

    private void checkCourseExists(Long courseId){
        try{
            courseServiceWebClient.get()
                    .uri("/courses/{id}", courseId)
                    .retrieve()
                    .bodyToMono(CourseDto.class)
                    .block();
        }catch (Exception e){
            throw new RuntimeException("Error while retrieving the course");
        }
    }

    private EnrollmentResponseDto toDto(Enrollment enrollment) {
        return new EnrollmentResponseDto(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollment.getEnrolledAt(),
                enrollment.getStatus()
        );
    }

}
