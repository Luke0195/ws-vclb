package br.com.dscatalog.application.controllers.exceptions;

import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<StandardError> entityAlreadyExists(HttpServletRequest request, ResourceAlreadyExists exception){
        StandardError responseData = new StandardError();
        responseData.setTimestamp(Instant.now());
        responseData.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.setError(exception.getMessage());
        responseData.setPath(request.getRequestURI());
        responseData.setMessage("This entity already exists");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> fieldValidationError(HttpServletRequest request, MethodArgumentNotValidException exception ){
        StandardError responseData = new StandardError();
        responseData.setTimestamp(Instant.now());
        responseData.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.setError("Please check the errors fields keys");
        responseData.setPath(request.getRequestURI());
        responseData.setMessage("Hibernate Validation Fails!");
        // Get all errors
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String fieldMessage = fieldError.getDefaultMessage();
            responseData.getFields().add( new FieldValidation(fieldName, fieldMessage));
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }
}
