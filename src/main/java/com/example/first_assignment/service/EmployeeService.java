package com.example.first_assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getActiveEmployee(Integer employeeId, String active) {
        return employeeRepository.findByEmployeeIdAndIsActive(employeeId, active);
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee updateEmployee(Integer employeeId, Employee employee) {

        if (employeeRepository.existsById(employeeId)) {
            employee.setEmployeeId(employeeId);
            return employeeRepository.save(employee);
        }

        return null;
    }
    
    public boolean deleteEmployee(Integer employeeId) {

        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return true;
        }

        return false;
    }
}