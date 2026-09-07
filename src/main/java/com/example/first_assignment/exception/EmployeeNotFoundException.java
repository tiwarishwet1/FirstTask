package com.example.first_assignment.exception;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Integer employeeId) {
        super("Employee with ID " + employeeId + " not found");
    }
}