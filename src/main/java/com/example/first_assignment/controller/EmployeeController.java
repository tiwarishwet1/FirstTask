package com.example.first_assignment.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.first_assignment.dto.*;
import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    // =========================================================
    // GET ALL / OWN EMPLOYEE
    // =========================================================

    @GetMapping
    public List<Employee> getAllEmployees(
            Authentication authentication) {

        return employeeService.getEmployeesForUser(
                authentication.getName());
    }
    @GetMapping("/salary/greater-than/{salary}")
    public ResponseEntity<List<Employee>> getEmployeesWithSalaryGreaterThan(
            @PathVariable double salary) {

        List<Employee> employees =
                employeeService
                    .getEmployeesWithSalaryGreaterThan(salary);

        return ResponseEntity.ok(employees);
    
    }    
    // =========================================================
    // GET EMPLOYEE BY ID
    // =========================================================

    @GetMapping("/{employeeId}")
    public List<Employee> getActiveEmployee(
            @PathVariable Integer employeeId,
            @RequestParam String active,
            Authentication authentication) {

        return employeeService.getEmployeesForUser(
                authentication.getName(),
                employeeId);
    }
    

    // =========================================================
    // POST
    // =========================================================
//
//    @PostMapping
//    public ResponseEntity<Employee> saveEmployee(
//            @Valid @RequestBody Employee employee) {
//
//        Employee savedEmployee =
//                employeeService.saveEmployee(employee);
//
//        return new ResponseEntity<>(
//                savedEmployee,
//                HttpStatus.CREATED);
//    }

    
    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(
            @Valid @RequestBody EmployeeRequest request) {

        Employee employee = employeeService.createEmployee(request);

        return new ResponseEntity<>(
                employeeService.toResponse(employee),
                HttpStatus.CREATED);
    }
    // =========================================================
    // PUT
    // =========================================================

//    @PutMapping("/{employeeId}")
//    public Employee updateEmployee(
//            @PathVariable Integer employeeId,
//            @Valid @RequestBody Employee employee) {
//
//        return employeeService.updateEmployee(
//                employeeId,
//                employee);
//    }

    
    
    @PutMapping("/{employeeId}")
    public EmployeeResponse updateEmployee(
            @PathVariable Integer employeeId,
            @Valid @RequestBody EmployeeRequest request) {

        Employee employee =
                employeeService.updateEmployee(employeeId, request);

        return employeeService.toResponse(employee);
    }
    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Integer employeeId) {

        employeeService.deleteEmployee(employeeId);

        return ResponseEntity.noContent().build();
    }
}