package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductUseCaseImplementation productUseCaseImplementation;

    @Autowired
    public ProductController(ProductUseCaseImplementation productUseCaseImplementation) {
        this.productUseCaseImplementation = productUseCaseImplementation;
    }


    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAllPaged(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productUseCaseImplementation.findAllPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productUseCaseImplementation.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductDto dto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(productUseCaseImplementation.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(productUseCaseImplementation.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
