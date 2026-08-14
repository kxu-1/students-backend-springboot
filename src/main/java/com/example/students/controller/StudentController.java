package com.example.students.controller;

import com.example.students.dto.StudentDto;
import com.example.students.service.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping ("/api/students")
public class StudentController {

    private StudentService studentService;

    // add restapi
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentDto studentDto) {
        StudentDto savedStudent = studentService.createStudent(studentDto);
        return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
    }

    // get restapi
    @GetMapping ("{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") Long id) {
        StudentDto studentDto = studentService.getStudentById(id);
        return ResponseEntity.ok(studentDto);
    }

    // get all students restapi
    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        var students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    // update restapi
    @PutMapping ("{id}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("id") Long id,
                                                    @Valid @RequestBody StudentDto updatedStudent) {
        StudentDto s = studentService.updateStudent(id, updatedStudent);
        return ResponseEntity.ok(s);
    }

    // delete restapi
    @DeleteMapping ("{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }

    // search student by email using RequestParam
    @GetMapping ("search")
    public ResponseEntity<StudentDto> getStudentByEmail(@RequestParam("email") String email) {
        StudentDto studentDto = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(studentDto);
    }
}
