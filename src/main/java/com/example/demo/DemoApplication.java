package com.example.demo;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner createDefaultAdmin() {
		return args -> {

			User admin = userRepository.findByEmail("admin@gmail.com");

			if (admin == null) {

				User newAdmin = new User();
				newAdmin.setName("Main Admin");
				newAdmin.setEmail("admin@gmail.com");
				newAdmin.setPassword("admin123");
				newAdmin.setRole("ADMIN");

				userRepository.save(newAdmin);

				System.out.println("Default Admin Created");
			}
		};
	}
}