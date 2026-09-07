package com.example.first_assignment.security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class RegisterRequest {

    @NotNull(message = "Employee ID is required")
    private Integer employeeId;

    @NotBlank(message = "Employee name is required")
    @Pattern(
        regexp = "^[A-Za-z ]+$",
        message = "Employee name must contain only letters and spaces"
    )
    private String employeeName;

    @NotNull(message = "Employee salary is required")
    @Positive(message = "Employee salary must be greater than zero")
    private Double employeeSalary;

    @NotBlank(message = "Active flag is required")
    @Pattern(
        regexp = "Y|N",
        message = "Active flag must be Y or N"
    )
    private String isActive;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public RegisterRequest() {
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Double getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(Double employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}