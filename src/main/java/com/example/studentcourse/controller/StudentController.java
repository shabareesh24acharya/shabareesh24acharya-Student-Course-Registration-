package com.example.studentcourse.controller;

import com.example.studentcourse.model.Student;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final ArrayList<Student> students = new ArrayList<>();
    private int nextStudentId = 1;

    @PostMapping
    public ResponseEntity<Student> registerStudent(@Valid @RequestBody Student student) {
        student.setStudentId(nextStudentId++);
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }

    @GetMapping
    public ResponseEntity<ArrayList<Student>> getAllStudents() {
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findStudentById(@PathVariable int id) {
        Optional<Student> student = findStudent(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student registration not found with ID: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegistration(@PathVariable int id) {
        Optional<Student> student = findStudent(id);
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student registration not found with ID: " + id);
        }
        students.remove(student.get());
        return ResponseEntity.ok("Student registration deleted successfully");
    }

    private Optional<Student> findStudent(int id) {
        return students.stream()
                .filter(student -> student.getStudentId() == id)
                .findFirst();
    }
}
