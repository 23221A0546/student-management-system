package com.example.demo.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgz3b3l48",
                "api_key", "275887529924475",
                "api_secret", "tyAkZbFAGbjl9PM-4rpIYzmY3-U",
                "secure", true
        ));
    }
}