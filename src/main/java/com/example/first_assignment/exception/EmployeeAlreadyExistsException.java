package com.example.first_assignment.exception;

public class EmployeeAlreadyExistsException extends RuntimeException {

    public EmployeeAlreadyExistsException(Integer employeeId) {
        super("Employee with ID " + employeeId + " already exists");
    }
}