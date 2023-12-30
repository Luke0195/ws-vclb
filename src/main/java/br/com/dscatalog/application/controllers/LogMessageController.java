package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.LogMessageDto;
import br.com.dscatalog.application.services.implementations.LogMessageUseCaseImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/logs")
public class LogMessageController {
    private final LogMessageUseCaseImplementation logMessageUseCaseImplementation;

    @Autowired
    public LogMessageController(LogMessageUseCaseImplementation logMessageUseCaseImplementation) {
        this.logMessageUseCaseImplementation = logMessageUseCaseImplementation;
    }

    @GetMapping
    public ResponseEntity<List<LogMessageDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(logMessageUseCaseImplementation.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LogMessageDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(logMessageUseCaseImplementation.findErrorMessageById(id));
    }
}
