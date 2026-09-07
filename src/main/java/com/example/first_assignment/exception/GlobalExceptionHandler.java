package com.example.first_assignment.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//import com.example.first_assignment.exception.EmployeeAlreadyExistsException;
//import com.example.first_assignment.exception.EmployeeNotFoundException;
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String, String>> handleValidationException(
//            MethodArgumentNotValidException ex) {
//
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult()
//          .getFieldErrors()
//          .forEach(error ->
//              errors.put(error.getField(), error.getDefaultMessage())
//          );
//
//        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
//    }
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Map<String, String>> handleAccessDenied(
	        AccessDeniedException ex) {

	    Map<String, String> error = new HashMap<>();
	    error.put("error", ex.getMessage());

	    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleEmployeeNotFound(
	        EmployeeNotFoundException ex) {

	    Map<String, String> error = new HashMap<>();
	    error.put("error", ex.getMessage());

	    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(EmployeeAlreadyExistsException.class)
	public ResponseEntity<Map<String, String>> handleEmployeeAlreadyExists(
	        EmployeeAlreadyExistsException ex) {

	    Map<String, String> error = new HashMap<>();
	    error.put("error", ex.getMessage());

	    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	
//	@ExceptionHandler(IllegalArgumentException.class)
//	public ResponseEntity<Map<String, String>> handleIllegalArgument(
//	        IllegalArgumentException ex) {
//
//	    Map<String, String> error = new HashMap<>();
//	    error.put("error", ex.getMessage());
//
//	    return new ResponseEntity<>(
//	            error,
//	            HttpStatus.CONFLICT);
//	}
	
}