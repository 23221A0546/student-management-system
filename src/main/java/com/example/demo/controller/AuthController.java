package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Student;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import com.example.demo.service.StudentService;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

//    @GetMapping("/admin-register")
//    public String adminRegisterPage() {
//        return "admin-register";
//    }
//
//    @PostMapping("/register-admin")
//    public String registerAdmin(Admin admin) {
//
//        adminService.saveAdmin(admin);
//
//        return "redirect:/login";
//    }
    // REGISTER PAGE
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // REGISTER USER
    @PostMapping("/register")
    public String register(User user,
                           @RequestParam("photoFile") MultipartFile photoFile)
            throws IOException {

        // Upload folder
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Generate unique file name
        String fileName = System.currentTimeMillis() + "_"
                + photoFile.getOriginalFilename();

        // Save photo
        photoFile.transferTo(new File(uploadDir + fileName));
        user.setRole("STUDENT");

        // Save user
        userService.registerUser(user);

        // Create student record
        Student student = new Student();
        student.setName(user.getName());
        student.setEmail(user.getEmail());
        student.setPhoto(fileName);

        studentService.saveStudent(student);

        return "redirect:/";
    }

    // LOGIN
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(String username,
                        String password) {

        User user = userService.findByEmail(username);

        if (user == null) {
            return "redirect:/";
        }

        if (!user.getPassword().equals(password)) {
            return "redirect:/";
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "redirect:/admin-dashboard";
        }

        return "redirect:/student-dashboard?email=" + user.getEmail();
    }

}