package com.example.demo.controller;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.service.UserService;
import com.example.demo.entity.Notice;
import com.example.demo.entity.Student;
import com.example.demo.service.EmailService;
import com.example.demo.service.NoticeService;
import com.example.demo.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private NoticeService noticeService;

    @Autowired
    private EmailService emailService;
@Autowired
private  UserService userService;
@Autowired
private Cloudinary cloudinary;
    @GetMapping("/admin-dashboard")
    public String adminDashboard(
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Student> students;

        if (keyword != null && !keyword.trim().isEmpty()) {
            students = studentService.searchStudents(keyword);
        } else {
            students = studentService.getAllStudents();
        }

        model.addAttribute("students", students);

        // Pie Chart Data
        model.addAttribute("totalStudents",
                studentService.getAllStudents().size());

        model.addAttribute("totalUsers",
                userService.getAllUsers().size());

        model.addAttribute("totalNotices",
                noticeService.getAllNotices().size());

        model.addAttribute("keyword", keyword);

        return "admin-dashboard";
    }

    @GetMapping("/delete-student/{id}")
    public String deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return "redirect:/admin-dashboard";
    }

    @GetMapping("/edit-student/{id}")
    public String editStudent(@PathVariable Long id,
                              Model model) {
        Student student =studentService.getStudentById(id);

        model.addAttribute("student",
                student);

        return "edit-student";
    }

    @PostMapping("/update-student/{id}")
    public String updateStudent(
            @PathVariable Long id,
            Student student,
            @RequestParam("photoFile") MultipartFile photoFile)
            throws IOException {

        if (!photoFile.isEmpty()) {

            String uploadDir =
                    System.getProperty("user.dir") + "/uploads/";

            File dir = new File(uploadDir);

            if (!dir.exists()) {
                dir.mkdirs();
            }

            String fileName =
                    System.currentTimeMillis() + "_"
                            + photoFile.getOriginalFilename();

            Map uploadResult = cloudinary.uploader().upload(
                    photoFile.getBytes(),
                    ObjectUtils.emptyMap()
            );

            String imageUrl = uploadResult.get("secure_url").toString();

            student.setPhoto(imageUrl);
        } else {

            Student existingStudent =
                    studentService.getStudentById(id);

            if (existingStudent != null) {
                student.setPhoto(existingStudent.getPhoto());
            }
        }

        studentService.updateStudent(id, student);

        return "redirect:/admin-dashboard";
    }

    @GetMapping("/add-student")
    public String addStudentPage() {
        return "add-student";
    }

    @PostMapping("/save-student")
    public String saveStudent( Student student, @RequestParam("photoFile") MultipartFile file) {

        studentService.saveStudent(student);

        return "redirect:/admin-dashboard";
    }




        @GetMapping("/view-student/{id}")
        public String viewStudent(@PathVariable Long id, Model model) {

            Student student = studentService.getStudentById(id);

            model.addAttribute("student", student);

            return "student-profile";
        }
    @PostMapping("/save-notice")
    public String saveNotice(
            @RequestParam String title,
            @RequestParam String description) {

        Notice notice = new Notice();

        notice.setTitle(title);
        notice.setDescription(description);

        noticeService.saveNotice(notice);

        List<Student> students = studentService.getAllStudents();

        for (Student student : students) {
            try {
                emailService.sendNoticeMail(
                        student.getEmail(),
                        title,
                        description
                );
            } catch (Exception e) {
                System.out.println(
                        "Failed to send email to: "
                                + student.getEmail()
                );
            }
        }

        return "redirect:/admin-dashboard";
    }

    private static void extracted(String title, Notice notice) {
        notice.setTitle(title);
    }


}