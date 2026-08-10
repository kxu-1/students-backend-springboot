package com.example.students.mapper;

import com.example.students.dto.StudentDto;
import com.example.students.entity.Student;

public class StudentMapper {

    public static StudentDto mapToStudentDto(Student s) {
        return new StudentDto(
                s.getId(), s.getFirstName(), s.getLastName(), s.getEmail()
        );
    }

    public static Student mapToStudent(StudentDto s) {
        return new Student(
                s.getId(), s.getFirstName(), s.getLastName(), s.getEmail()
        );
    }
}
