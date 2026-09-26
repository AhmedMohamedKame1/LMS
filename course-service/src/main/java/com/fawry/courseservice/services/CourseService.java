package com.fawry.courseservice.services;

import com.fawry.courseservice.dtos.CourseResponseDto;
import com.fawry.courseservice.dtos.CreateCourseDto;
import com.fawry.courseservice.entities.Course;
import com.fawry.courseservice.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseResponseDto createCourse(CreateCourseDto dto,Long instructorId, String role){
        if(!role.equals("INSTRUCTOR")){
            throw new RuntimeException("Only instructors can create courses");
        }

        Course crs = new Course();
        crs.setTitle(dto.getTitle());
        crs.setDescription(dto.getDescription());
        crs.setInstructorId(instructorId);

        courseRepository.save(crs);
        return toDto(crs);
    }

    public List<CourseResponseDto> getAllCourses(){
        return courseRepository.findAll().stream().map(this::toDto).toList();
    }

    public CourseResponseDto getCourseById(Long id) {
        Course crs = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return toDto(crs);
    }

    private CourseResponseDto toDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getInstructorId()
        );
    }
}
