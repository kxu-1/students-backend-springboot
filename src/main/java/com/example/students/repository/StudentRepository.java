package com.example.students.repository;

import com.example.students.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

// already has @repository in one of its superclasses
public interface StudentRepository extends JpaRepository<Student, Long> {
}
