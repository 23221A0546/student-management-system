package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.NoticeService;
import com.example.demo.service.StudentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.NoticeService;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/student-dashboard")
    public String studentDashboard(@RequestParam String email,Model model) {
        Student student=studentService.getStudentByEmail(email);
        model.addAttribute("student", student);

        model.addAttribute(
                "notices",
                noticeService.getAllNotices()
        );
        return "student-dashboard";
    }



    @ResponseBody
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @ResponseBody
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }

    @ResponseBody
    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @ResponseBody
    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

    @ResponseBody
    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestBody Student student) {

        return studentService.updateStudent(id, student);
    }

}