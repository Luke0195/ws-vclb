package br.com.dscatalog.application.controllers.exceptions;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.services.exceptions.DatabaseException;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.implementations.LogMessageUseCaseImplementation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final LogMessageUseCaseImplementation implementation;

    @Autowired
    public ControllerExceptionHandler(LogMessageUseCaseImplementation implementation) {
        this.implementation = implementation;
    }

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<StandardError> handleEntityAlreadyExists(HttpServletRequest request, ResourceAlreadyExists exception) {
        StandardError responseData = new StandardError();
        responseData.setTimestamp(Instant.now());
        responseData.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.setError(exception.getMessage());
        responseData.setPath(request.getRequestURI());
        responseData.setMessage("This entity already exists");
        LogMessageDto dto = new LogMessageDto();
        dto.setCreatedAt(Instant.now());
        dto.setDescription(exception.getMessage());
        dto.setEndpoint(request.getRequestURI());
        this.implementation.create(dto);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleFieldValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
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
            responseData.getFields().add(new FieldValidation(fieldName, fieldMessage));
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(ResourceNotExists.class)
    public ResponseEntity<StandardError> handleEntityNotFound(HttpServletRequest request, ResourceNotExists exception) {
        StandardError responseData = new StandardError();
        responseData.setTimestamp(Instant.now());
        responseData.setStatus(HttpStatus.NOT_FOUND.value());
        responseData.setError(exception.getMessage());
        responseData.setPath(request.getRequestURI());
        responseData.setMessage("Entity not found!");
        LogMessageDto dto = new LogMessageDto();
        dto.setCreatedAt(Instant.now());
        dto.setDescription(exception.getMessage());
        dto.setEndpoint(request.getRequestURI());
        this.implementation.create(dto);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleIntegrateViolation(HttpServletRequest request, DatabaseException e ){
        StandardError responseData = new StandardError();
        responseData.setTimestamp(Instant.now());
        responseData.setStatus(HttpStatus.BAD_REQUEST.value());
        responseData.setError(e.getMessage());
        responseData.setPath(request.getRequestURI());
        responseData.setMessage("Database Exception!");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);

    }


}
