package com.example.students.service.impl;

import com.example.students.dto.StudentDto;
import com.example.students.entity.Student;
import com.example.students.exception.ResourceNotFoundException;
import com.example.students.mapper.StudentMapper;
import com.example.students.repository.StudentRepository;
import com.example.students.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());

        Student updatedStudentObj = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));

        studentRepository.deleteById(id);
    }
}
