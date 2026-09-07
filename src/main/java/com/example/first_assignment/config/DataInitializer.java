package com.example.first_assignment.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.first_assignment.entity.AppUser;
import com.example.first_assignment.repository.AppUserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (appUserRepository.findByUsername("admin").isEmpty()) {

                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                admin.setEmployeeId(null);

                appUserRepository.save(admin);
            }

            if (appUserRepository.findByUsername("rahul").isEmpty()) {

                AppUser employee = new AppUser();
                employee.setUsername("rahul");
                employee.setPassword(passwordEncoder.encode("rahul123"));
                employee.setRole("EMPLOYEE");
                employee.setEmployeeId(101);

                appUserRepository.save(employee);
            }

            if (appUserRepository.findByUsername("priya").isEmpty()) {

                AppUser employee = new AppUser();
                employee.setUsername("priya");
                employee.setPassword(passwordEncoder.encode("priya123"));
                employee.setRole("EMPLOYEE");
                employee.setEmployeeId(102);

                appUserRepository.save(employee);
            }
        };
    }
}