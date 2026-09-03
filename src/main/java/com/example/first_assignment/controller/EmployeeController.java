////package com.example.first_assignment.controller;
////
////import java.util.List;
////
////import org.springframework.web.bind.annotation.GetMapping;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////
////import com.example.first_assignment.entity.Employee;
////import com.example.first_assignment.repository.EmployeeRepository;
////
////@RestController
////@RequestMapping("/employees")
////public class EmployeeController {
////
////    private final EmployeeRepository employeeRepository;
////
////    public EmployeeController(EmployeeRepository employeeRepository) {
////        this.employeeRepository = employeeRepository;
////    }
////
////    @GetMapping
////    public List<Employee> getAllEmployees() {
////        return employeeRepository.findAll();
////    }
////}
//
//
//
//package com.example.first_assignment.controller;
//
//import java.util.List;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.first_assignment.entity.Employee;
////import com.example.first_assignment.repository.EmployeeRepository;
//import com.example.first_assignment.service.EmployeeService;
//
//@RestController
//@RequestMapping("/employees")
//public class EmployeeController {
//
////    private final EmployeeRepository employeeRepository;
////
////    public EmployeeController(EmployeeRepository employeeRepository) {
////        this.employeeRepository = employeeRepository;
////    }
//
//    @GetMapping
//    public List<Employee> getAllEmployees() {
//        return employeeRepository.findAll();
//    }
//
//    @GetMapping("/{employeeId}")
//    public List<Employee> getActiveEmployee(
//            @PathVariable Integer employeeId,
//            @RequestParam String active) {
//
//        return employeeRepository.findByEmployeeIdAndIsActive(employeeId, active);
//    }
//    
//    @PostMapping
//    public Employee saveEmployee(@RequestBody Employee employee) {
//        return employeeService.saveEmployee(employee);
//    }
//}

package com.example.first_assignment.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import com.example.first_assignment.entity.Employee;
import com.example.first_assignment.service.EmployeeService;
import org.springframework.web.bind.annotation.DeleteMapping;
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{employeeId}")
    public List<Employee> getActiveEmployee(
            @PathVariable Integer employeeId,
            @RequestParam String active) {

        return employeeService.getActiveEmployee(employeeId, active);
    }

    @PostMapping
    public Employee saveEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }
    
    @PutMapping("/{employeeId}")
    public String updateEmployee(
            @PathVariable Integer employeeId,
            @Valid @RequestBody Employee employee) {

        Employee updatedEmployee =
                employeeService.updateEmployee(employeeId, employee);

        if (updatedEmployee != null) {
            return "Employee updated successfully";
        }

        return "Employee not found";
    }
    
    @DeleteMapping("/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId) {

        boolean deleted = employeeService.deleteEmployee(employeeId);

        if (deleted) {
            return "Employee deleted successfully";
        }

        return "Employee not found";
    }
}