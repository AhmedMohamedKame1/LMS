package com.fawry.enrollmentservice.dtos;

import com.fawry.enrollmentservice.entities.EnrollmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class EnrollmentResponseDto {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Date enrolledAt;
    private EnrollmentStatus status;
}
