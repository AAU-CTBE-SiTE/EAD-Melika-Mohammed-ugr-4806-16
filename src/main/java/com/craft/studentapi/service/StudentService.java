package com.craft.studentapi.service;

import com.craft.studentapi.model.Student;
import com.craft.studentapi.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student create(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    public Student getById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student update(Long id, Student updated) {
        Student existing = studentRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        return studentRepository.save(existing);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}