package br.com.dscatalog.application.controllers.exceptions;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.mappers.LogMessageMapper;
import br.com.dscatalog.application.mappers.StandardErrorMapper;
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

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Integer BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    private static final Integer NOT_FOUND = HttpStatus.NOT_FOUND.value();

    @ExceptionHandler(ResourceAlreadyExists.class)
    public ResponseEntity<StandardError> handleEntityAlreadyExists(HttpServletRequest request, ResourceAlreadyExists exception) {
        StandardError responseData = StandardErrorMapper.makeStandardError(
                BAD_REQUEST, exception.getMessage(), request.getRequestURI(),
                "This entity already exists", null);
        LogMessageDto dto = LogMessageMapper.makeLogMessage(exception.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> handleFieldValidationError(HttpServletRequest request, MethodArgumentNotValidException exception) {
        List<FieldValidation> fieldValidations = new ArrayList<>();
        exception.getFieldErrors().forEach(fieldError -> {
            String fieldName = fieldError.getField();
            String fieldMessage = fieldError.getDefaultMessage();
            fieldValidations.add(new FieldValidation(fieldName, fieldMessage));
        });
        StandardError responseData = StandardErrorMapper.makeStandardError(
                BAD_REQUEST, exception.getMessage(), request.getRequestURI(),
                "Please check the errors fields keys", fieldValidations);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
    }

    @ExceptionHandler(ResourceNotExists.class)
    public ResponseEntity<StandardError> handleEntityNotFound(HttpServletRequest request, ResourceNotExists exception) {
        StandardError responseData = StandardErrorMapper.makeStandardError(
                NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI(),
                "Entity Not Found!",
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> handleIntegrateViolation(HttpServletRequest request, DatabaseException e) {
        StandardError responseData = StandardErrorMapper.makeStandardError(
                BAD_REQUEST,
                e.getMessage(),
                request.getRequestURI(),
                "Database Exception",
                null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseData);
    }


}
