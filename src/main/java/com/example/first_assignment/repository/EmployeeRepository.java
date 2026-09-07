package com.example.first_assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.first_assignment.entity.Employee;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	List<Employee> findByEmployeeIdAndIsActive(Integer employeeId, String isActive);
    List<Employee> findByEmployeeSalaryGreaterThan(double EmployeeSalary );
    
} 