package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    private final ProductUseCaseImplementation productUseCaseImplementation;

    @Autowired
    public ProductController(ProductUseCaseImplementation productUseCaseImplementation){
        this.productUseCaseImplementation = productUseCaseImplementation;
    }


    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAllPaged(
        @RequestParam(value="page", defaultValue = "0") Integer page,
        @RequestParam(value="linesPerPage", defaultValue = "12") Integer linesPerPage,
        @RequestParam(value="direction", defaultValue="DESC") String direction,
        @RequestParam(value="orderBy", defaultValue = "name") String sort
    ){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), sort);
        return ResponseEntity.status(HttpStatus.OK).body(this.productUseCaseImplementation.findAllPaged(pageRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.productUseCaseImplementation.findById(id));
    }

}
