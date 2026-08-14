package com.example.students.repository;

import com.example.students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// already has @repository in one of its superclasses
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);
}
