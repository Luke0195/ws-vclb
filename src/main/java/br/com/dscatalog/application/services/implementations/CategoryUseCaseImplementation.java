package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.usecases.CategoryUseCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryUseCaseImplementation implements CategoryUseCases {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryUseCaseImplementation(CategoryRepository repository){
        this.categoryRepository = repository;
    }
    @Override
    @Transactional(readOnly = true)
    public CategoryDto create(CategoryDto dto) {
        var categoryAlreadyExists = categoryRepository.findByName(dto.getName());
        if(categoryAlreadyExists.isPresent()){
            throw new ResourceAlreadyExists("This category already exists");
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
       List<Category> entitiesCategories = categoryRepository.findAll();
       return entitiesCategories.stream().map(CategoryDto::new).toList();
    }
}
