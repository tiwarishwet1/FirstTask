package com.example.first_assignment.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Employee_details")
public class Employee {

    @Id
    @Column(name = "employee_id")
    private Integer employeeId;

    @NotBlank(message = "Employee name is required")
    @Pattern(
        regexp = "^[A-Za-z ]+$",
        message = "Employee name must contain only letters and spaces"
    )
    @Column(name = "employee_name")
    private String employeeName;

    @NotNull(message = "Employee salary is required")
    @Positive(message = "Employee salary must be greater than zero")
    @Column(name = "employee_salary")
    private Double employeeSalary;

    @NotBlank(message = "Active flag is required")
    @Pattern(
        regexp = "Y|N",
        message = "Active flag must be Y or N"
    )
    @Column(name = "is_active")
    private String isActive;

    @CreatedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    @CreatedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedDate
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @LastModifiedBy
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "updated_by")
    private String updatedBy;

    public Employee() {
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}