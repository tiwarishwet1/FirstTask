package com.example.first_assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.first_assignment.entity.Employee;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query("SELECT e FROM Employee e WHERE e.employeeId = :employeeId AND e.isActive = :isActive")
	List<Employee> findByEmployeeIdAndIsActive(Integer employeeId, String isActive);
    @Query("SELECT e FROM Employee e WHERE e.employeeSalary > 500 AND e.isActive = :isActive")
    findByEmpoloyeeIdAndIsActiveAndSaleryGreatentha ()

} 