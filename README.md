# Employee Management System - Spring Boot Assignment

## Project Overview

This project is a Spring Boot REST API developed as part of the Spring project assignment. It implements an Employee Management System with:

- RESTful Employee APIs
- MySQL database integration using Spring Data JPA
- Employee entity and repository
- CRUD operations
- Input validation
- Global exception handling
- JPA Auditing
- User registration and login
- JWT-based authentication
- Role-based authorization for ADMIN and EMPLOYEE users
- Employee ownership-based access control
- DTO-based request and response handling
- Custom salary-based employee search

---

## Assignment Requirements Covered

The assignment requires creating a Spring project, implementing GET and POST APIs, testing APIs using Postman, connecting the application to MySQL, creating a database table with sample data, creating an Entity and Repository, and fetching employee data using `employee_id` and the active flag.

This implementation extends the basic requirements with validation, exception handling, JWT security, role-based access control, auditing, DTOs, and complete CRUD operations.

---

## Project Structure

```text
com.example.first_assignment
│
├── FirstAssignmentApplication.java
│
├── config
│   ├── AuditorConfig.java
│   ├── DataInitializer.java
│   ├── PasswordConfig.java
│   └── SecurityConfig.java
│
├── controller
│   ├── AuthController.java
│   └── EmployeeController.java
│
├── dto
│   ├── EmployeeRequest.java
│   ├── EmployeeResponse.java
│   └── LoginResponse.java
│
├── entity
│   ├── AppUser.java
│   └── Employee.java
│
├── exception
│   ├── AccessDeniedException.java
│   ├── EmployeeAlreadyExistsException.java
│   ├── EmployeeNotFoundException.java
│   ├── GlobalExceptionHandler.java
│   └── UserAlreadyExistsException.java
│
├── repository
│   ├── AppUserRepository.java
│   └── EmployeeRepository.java
│
├── security
│   ├── JwtService.java
│   ├── LoginRequest.java
│   └── RegisterRequest.java
│
└── service
    ├── CustomUserDetailsService.java
    ├── EmployeeService.java
    └── RegistrationService.java
```

---

# Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Application framework |
| Spring Web | REST API development |
| Spring Data JPA | Database interaction |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication |
| MySQL | Relational database |
| Hibernate | ORM implementation |
| Jakarta Validation | Request validation |
| Postman | API testing |
| Maven | Dependency management |

---

# Database Design

## Employee Table

Table name:

```text
Employee_details
```

| Column | Description |
|---|---|
| employee_id | Primary key |
| employee_name | Employee name |
| employee_salary | Employee salary |
| is_active | Employee active status (`Y` or `N`) |
| created_on | Record creation timestamp |
| created_by | User who created the record |
| updated_on | Last update timestamp |
| updated_by | User who last updated the record |

The table structure follows the employee fields specified in the assignment.

## User Table

Table name:

```text
app_users
```

| Column | Description |
|---|---|
| user_id | Primary key |
| username | Unique username |
| password | BCrypt encrypted password |
| role | ADMIN or EMPLOYEE |
| employee_id | Associated employee ID |

---

# Application Features

## 1. Employee Management

The application supports:

- Create Employee
- Get all Employees
- Get Employee by ID
- Update Employee
- Delete Employee
- Get Employees with salary greater than a specified value

## 2. Validation

Employee input is validated using Jakarta Validation.

### Employee Name

Only letters and spaces are allowed.

Example invalid input:

```json
{
  "employeeName": "John123"
}
```

Example response:

```json
{
  "employeeName": "Employee name must contain only letters and spaces"
}
```

### Employee Salary

Salary must be greater than zero.

### Active Flag

Only the following values are accepted:

```text
Y
N
```

---

# Authentication and Authorization

## Roles

### ADMIN

ADMIN users can:

- View all employees
- View any employee
- Create employees
- Update employees
- Delete employees

### EMPLOYEE

EMPLOYEE users can:

- View their own active employee record
- Access only their associated employee ID

An EMPLOYEE attempting to access another employee's data receives an access denied response.

---

# Default Users

The application initializes the following users:

| Username | Password | Role |
|---|---|---|
| admin | admin123 | ADMIN |
| rahul | rahul123 | EMPLOYEE |
| priya | priya123 | EMPLOYEE |

Passwords are stored using BCrypt encryption.

---

# API Endpoints

## Authentication APIs

### Register Employee

```http
POST /auth/register
```

Sample Request:

```json
{
  "employeeId": 103,
  "employeeName": "Rohan Verma",
  "employeeSalary": 60000,
  "isActive": "Y",
  "username": "rohan",
  "password": "rohan123"
}
```

Expected Response:

```text
201 Created
```

---

### Login

```http
POST /auth/login
```

Sample Request:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Expected Response:

A JWT token is returned.

---

# Employee APIs

> All Employee APIs require JWT authentication.

Add the token in Postman:

```text
Authorization: Bearer <JWT_TOKEN>
```

---

## Get Employees

```http
GET /employees
```

### ADMIN

Returns all employees.

### EMPLOYEE

Returns only the logged-in employee's active record.

---

## Get Employee by ID

```http
GET /employees/{employeeId}?active=Y
```

Example:

```http
GET /employees/101?active=Y
```

### ADMIN

Can access any employee.

### EMPLOYEE

Can access only their own employee record.

---

## Create Employee

```http
POST /employees
```

Sample Request:

```json
{
  "employeeId": 104,
  "employeeName": "Amit Sharma",
  "employeeSalary": 55000,
  "isActive": "Y"
}
```

Expected Response:

```text
201 Created
```

ADMIN access required.

---

## Update Employee

```http
PUT /employees/{employeeId}
```

Sample Request:

```json
{
  "employeeId": 104,
  "employeeName": "Amit Kumar",
  "employeeSalary": 65000,
  "isActive": "Y"
}
```

ADMIN access required.

---

## Delete Employee

```http
DELETE /employees/{employeeId}
```

Expected Response:

```text
204 No Content
```

ADMIN access required.

---

## Get Employees by Salary

```http
GET /employees/salary/greater-than/{salary}
```

Example:

```http
GET /employees/salary/greater-than/50000
```

Returns employees whose salary is greater than the provided value.

---

# Exception Handling

The application includes centralized exception handling using:

```text
@RestControllerAdvice
```

Handled scenarios include:

| Scenario | HTTP Status |
|---|---|
| Validation failure | 400 Bad Request |
| Unauthorized employee access | 403 Forbidden |
| Employee not found | 404 Not Found |
| Employee already exists | 409 Conflict |
| Username already exists | 409 Conflict |
| Unexpected error | 500 Internal Server Error |

Example duplicate employee response:

```json
{
  "error": "Employee with ID 104 already exists"
}
```

---

# JPA Auditing

The Employee entity supports automatic auditing fields:

```text
createdOn
createdBy
updatedOn
updatedBy
```

JPA Auditing automatically manages timestamps, while the authenticated user is used as the auditor where authentication information is available.

---

# Application Flow

```text
Client / Postman
       |
       v
Controller
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database
```

For secured requests:

```text
Login
  |
  v
AuthenticationManager
  |
  v
JWT Generated
  |
  v
JWT Sent in Authorization Header
  |
  v
Spring Security Validates JWT
  |
  v
Controller
  |
  v
Service Authorization Check
  |
  v
Database
```

---

# How to Run the Project

## Prerequisites

Install:

- Java
- Maven
- MySQL Server
- MySQL Workbench
- Postman
- Spring Tool Suite / IntelliJ IDEA / Eclipse

## Step 1: Create Database

Create the required MySQL database.

Example:

```sql
CREATE DATABASE employee_db;
```

## Step 2: Configure Database

Configure your datasource properties in:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Update the values according to your local MySQL setup.

## Step 3: Run the Application

Run:

```text
FirstAssignmentApplication.java
```

The application should start successfully.

## Step 4: Login

Use:

```text
POST /auth/login
```

Example:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Copy the JWT token returned by the API.

## Step 5: Test Secured APIs

In Postman:

```text
Authorization
→ Bearer Token
→ Paste JWT Token
```

Test the Employee APIs.

---

# Suggested Submission Screenshots

Add screenshots to a folder named:

```text
screenshots/
```

Recommended screenshots:

```text
01-project-structure.png
02-mysql-database.png
03-employee-table.png
04-application-running.png
05-login-jwt.png
06-get-all-employees.png
07-get-employee-by-id.png
08-create-employee.png
09-update-employee.png
10-delete-employee.png
11-salary-filter.png
12-validation-error.png
13-duplicate-error.png
14-access-denied.png
```

The most important screenshots for assignment evidence are:

1. Spring Boot project structure
2. MySQL database/schema
3. Employee table and sample data
4. Application running successfully
5. Postman API testing
6. Employee lookup using employee ID and active status

---

# Project Highlights

- Layered Spring Boot architecture
- RESTful APIs
- MySQL and Spring Data JPA
- Repository query methods
- DTO pattern
- Input validation
- Global exception handling
- BCrypt password encryption
- JWT authentication
- Role-based authorization
- Employee ownership validation
- JPA auditing
- CRUD operations

---

# Author

**Assignment Submission**

Employee Management System using Spring Boot, MySQL, Spring Security, and JWT.
