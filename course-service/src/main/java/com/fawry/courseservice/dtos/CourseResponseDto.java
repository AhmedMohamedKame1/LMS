package com.fawry.courseservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CourseResponseDto {
    private Long id;
    private String title;
    private String description;
    private Long instructorId;
}
