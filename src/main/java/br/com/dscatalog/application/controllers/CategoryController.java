package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.entities.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value="/categories")
public class CategoryController {

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "Books"));
        list.add(new Category(2L, "Garden"));
        list.add(new Category(3L, "Eletronics"));
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
}
