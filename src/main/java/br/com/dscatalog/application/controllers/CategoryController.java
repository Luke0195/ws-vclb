package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.services.implementations.CategoryUseCaseImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {
    private final CategoryUseCaseImplementation categoryImplementation;

    @Autowired
    public CategoryController(CategoryUseCaseImplementation implementation){
        this.categoryImplementation = implementation;
    }
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryImplementation.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> categoryById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(categoryImplementation.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryImplementation.create(dto));
    }
}
