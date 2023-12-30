package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.services.implementations.CategoryUseCaseImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {
    private final CategoryUseCaseImplementation categoryImplementation;

    @Autowired
    public CategoryController(CategoryUseCaseImplementation implementation) {
        this.categoryImplementation = implementation;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(categoryImplementation.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> categoryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(categoryImplementation.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        dto = this.categoryImplementation.create(dto);
        // Add location on header request.
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
        dto = categoryImplementation.updateCategory(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryImplementation.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
