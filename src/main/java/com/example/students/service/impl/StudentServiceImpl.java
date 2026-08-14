package com.example.students.service.impl;

import com.example.students.dto.StudentDto;
import com.example.students.entity.Student;
import com.example.students.exception.ResourceNotFoundException;
import com.example.students.mapper.StudentMapper;
import com.example.students.repository.StudentRepository;
import com.example.students.service.EmailService;
import com.example.students.service.StudentService;
import com.example.students.util.SimpleLogger;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private EmailService emailService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public StudentDto createStudent(StudentDto studentDto) {
        SimpleLogger.info("Creating student with email: {}", studentDto.getEmail());
        Student student = StudentMapper.mapToStudent(studentDto);
        Student savedStudent = studentRepository.save(student);

        // Async task invocation
        emailService.sendEmail(savedStudent.getEmail());

        return StudentMapper.mapToStudentDto(savedStudent);
    }

    @Override
    @Cacheable(value = "students", key = "#id")
    public StudentDto getStudentById(Long id) {
        SimpleLogger.info("Fetching student with ID: {} (Cache miss)", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));
        return StudentMapper.mapToStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        SimpleLogger.info("Fetching all students");
        List<Student> students = studentRepository.findAll();
        return students.stream().map(StudentMapper::mapToStudentDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "students", key = "#id")
    public StudentDto updateStudent(Long id, StudentDto updatedStudent) {
        SimpleLogger.info("Updating student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));

        student.setFirstName(updatedStudent.getFirstName());
        student.setLastName(updatedStudent.getLastName());
        student.setEmail(updatedStudent.getEmail());

        Student updatedStudentObj = studentRepository.save(student);

        return StudentMapper.mapToStudentDto(updatedStudentObj);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "students", key = "#id")
    public void deleteStudent(Long id) {
        SimpleLogger.info("Deleting student with ID: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " does not exist"));

        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto getStudentByEmail(String email) {
        SimpleLogger.info("Fetching student with email: {}", email);
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student with email " + email + " does not exist"));
        return StudentMapper.mapToStudentDto(student);
    }
}
