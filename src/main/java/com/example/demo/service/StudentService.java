package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Save student
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    // Get student by Email
    public Student getStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    // Update student
    public Student updateStudent(Long id, Student student) {

        Student existingStudent =
                studentRepository.findById(id).orElse(null);

        if (existingStudent != null) {

            existingStudent.setCgpa(student.getCgpa());
            existingStudent.setBacklogs(student.getBacklogs());
            existingStudent.setTotalFees(student.getTotalFees());
            existingStudent.setFeesPaid(student.getFeesPaid());
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setMobile(student.getMobile());
            existingStudent.setCourse(student.getCourse());

            existingStudent.setBranch(student.getBranch());
            existingStudent.setAttendance(student.getAttendance());
            existingStudent.setProgress(student.getProgress());

            if (student.getPhoto() != null &&
                    !student.getPhoto().isEmpty()) {

                existingStudent.setPhoto(student.getPhoto());
            }
            existingStudent.setCgpa(student.getCgpa());
            existingStudent.setBacklogs(student.getBacklogs());
            existingStudent.setTotalFees(student.getTotalFees());
            existingStudent.setFeesPaid(student.getFeesPaid());

            return studentRepository.save(existingStudent);
        }

        return null;
    }

    // Delete student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Total students count
    public long getTotalStudents() {
        return studentRepository.count();
    }

    // Search students by name
    public List<Student> searchStudents(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return studentRepository.findAll();
        }

        return studentRepository
                .findByNameContainingIgnoreCase(keyword);
    }

}