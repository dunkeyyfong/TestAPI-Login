package com.nhom3.test.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }

    public String hashPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
