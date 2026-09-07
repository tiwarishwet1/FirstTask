package com.example.first_assignment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.first_assignment.entity.AppUser;
import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.exception.AccessDeniedException;
import com.example.first_assignment.exception.EmployeeAlreadyExistsException;
import com.example.first_assignment.exception.EmployeeNotFoundException;
import com.example.first_assignment.repository.AppUserRepository;
import com.example.first_assignment.repository.EmployeeRepository;
import com.example.first_assignment.dto.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            AppUserRepository appUserRepository) {

        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
    }

    // =========================================================
    // GET ALL / EMPLOYEE-SPECIFIC DATA
    // =========================================================

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    public List<Employee> getActiveEmployee(
            Integer employeeId,
            String active) {

        return employeeRepository
                .findByEmployeeIdAndIsActive(employeeId, active);
    }

    // =========================================================
    // POST
    // =========================================================

    public Employee saveEmployee(Employee employee) {

        if (employeeRepository.existsById(employee.getEmployeeId())) {

            throw new EmployeeAlreadyExistsException(
                    employee.getEmployeeId());
        }

        // createdBy, createdOn, updatedBy and updatedOn
        // are automatically handled by JPA Auditing.

        return employeeRepository.save(employee);
    }

    // =========================================================
    // PUT
    // =========================================================

    public Employee updateEmployee(
            Integer employeeId,
            Employee employee) {

        Employee existingEmployee =
                employeeRepository.findById(employeeId)
                        .orElseThrow(() ->
                                new EmployeeNotFoundException(employeeId));

        // Only fields that the client is allowed to modify
        // are copied from the request.

        existingEmployee.setEmployeeName(
                employee.getEmployeeName());

        existingEmployee.setEmployeeSalary(
                employee.getEmployeeSalary());

        existingEmployee.setIsActive(
                employee.getIsActive());

        // DO NOT modify:
        // employeeId
        // createdOn
        // createdBy
        // updatedOn
        // updatedBy

        return employeeRepository.save(existingEmployee);
    }

    // =========================================================
    // DELETE
    // =========================================================

    public void deleteEmployee(Integer employeeId) {

        if (!employeeRepository.existsById(employeeId)) {

            throw new EmployeeNotFoundException(employeeId);
        }

        employeeRepository.deleteById(employeeId);
    }

    // =========================================================
    // GET EMPLOYEE BY ID WITH OWNERSHIP CHECK
    // =========================================================

    public List<Employee> getEmployeesForUser(
            String username,
            Integer employeeId) {

        AppUser user =
                appUserRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        // ADMIN can access any employee
        if ("ADMIN".equals(user.getRole())) {

            return employeeRepository
                    .findByEmployeeIdAndIsActive(
                            employeeId,
                            "Y");
        }

        // EMPLOYEE can access only their own employee ID
        if (!employeeId.equals(user.getEmployeeId())) {

            throw new AccessDeniedException(
                    "You are not authorized to access this employee");
        }

        return employeeRepository
                .findByEmployeeIdAndIsActive(
                        employeeId,
                        "Y");
    }

    // =========================================================
    // GET /employees
    // =========================================================

    public List<Employee> getEmployeesForUser(
            String username) {

        AppUser user =
                appUserRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new RuntimeException("User not found"));

        // ADMIN gets all employees
        if ("ADMIN".equals(user.getRole())) {

            return employeeRepository.findAll();
        }

        // EMPLOYEE gets only their own active record
        return employeeRepository
                .findByEmployeeIdAndIsActive(
                        user.getEmployeeId(),
                        "Y");
    }
    
    public Employee createEmployee(EmployeeRequest request) {

        if (employeeRepository.existsById(request.getEmployeeId())) {
            throw new EmployeeAlreadyExistsException(request.getEmployeeId());
        }

        Employee employee = new Employee();

        employee.setEmployeeId(request.getEmployeeId());
        employee.setEmployeeName(request.getEmployeeName());
        employee.setEmployeeSalary(request.getEmployeeSalary());
        employee.setIsActive(request.getIsActive());

        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(
            Integer employeeId,
            EmployeeRequest request) {

        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        existingEmployee.setEmployeeName(request.getEmployeeName());
        existingEmployee.setEmployeeSalary(request.getEmployeeSalary());
        existingEmployee.setIsActive(request.getIsActive());

        return employeeRepository.save(existingEmployee);
    }

    public EmployeeResponse toResponse(Employee employee) {

        return new EmployeeResponse(
                employee.getEmployeeId(),
                employee.getEmployeeName(),
                employee.getEmployeeSalary(),
                employee.getIsActive(),
                employee.getCreatedOn(),
                employee.getCreatedBy(),
                employee.getUpdatedOn(),
                employee.getUpdatedBy()
        );
    }
}