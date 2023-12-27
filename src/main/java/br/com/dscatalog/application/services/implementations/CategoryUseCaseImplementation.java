package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.services.usecases.CategoryUseCases;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryUseCaseImplementation implements CategoryUseCases {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryUseCaseImplementation(CategoryRepository repository){
        this.categoryRepository = repository;
    }
    @Override
    public Category create(Category category) {
        return null;
    }

    @Override
    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
