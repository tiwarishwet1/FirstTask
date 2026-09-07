package com.example.first_assignment.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.first_assignment.entity.AppUser;
import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.exception.EmployeeAlreadyExistsException;
import com.example.first_assignment.repository.AppUserRepository;
import com.example.first_assignment.repository.EmployeeRepository;
import com.example.first_assignment.security.RegisterRequest;
import com.example.first_assignment.exception.*;
import com.example.first_assignment.service.*;

@Service
public class RegistrationService 
{

    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(
            EmployeeRepository employeeRepository,
            AppUserRepository appUserRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Employee registerEmployee(RegisterRequest request) {

        if (employeeRepository.existsById(request.getEmployeeId())) {
            throw new EmployeeAlreadyExistsException(
                    request.getEmployeeId());
        }

        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists");
        }   
        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new UserAlreadyExistsException(request.getUsername());
            }
        
        

        Employee employee = new Employee();

        employee.setEmployeeId(request.getEmployeeId());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeSalary(request.getEmployeeSalary());
        employee.setIsActive(request.getIsActive());

        Employee savedEmployee = employeeRepository.save(employee);

        AppUser appUser = new AppUser();

        appUser.setUsername(request.getUsername());
        appUser.setPassword(
                passwordEncoder.encode(request.getPassword()));

        // Public registration can ONLY create EMPLOYEE accounts.
        appUser.setRole("EMPLOYEE");

        appUser.setEmployeeId(savedEmployee.getEmployeeId());

        appUserRepository.save(appUser);

        return savedEmployee;
    }
}
