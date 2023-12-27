package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.services.implementations.CategoryUseCaseImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(categoryImplementation.findAll());
    }
}
