package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.mappers.CategoryMapper;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.usecases.CategoryUseCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryUseCaseImplementation implements CategoryUseCases {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryUseCaseImplementation(CategoryRepository repository) {
        this.categoryRepository = repository;
    }

    @Override
    @Transactional
    public CategoryDto create(CategoryDto dto) {
        var categoryAlreadyExists = categoryRepository.findByName(dto.getName());
        if (categoryAlreadyExists.isPresent()) {
            throw new ResourceAlreadyExists("This category already exists");
        }
        Category category = CategoryMapper.parseDtoToCategory(dto);
        category = categoryRepository.save(category);
        return CategoryMapper.parseEntityToDto(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        List<Category> entitiesCategories = categoryRepository.findAll();
        return entitiesCategories.stream().map(CategoryMapper::parseEntityToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        Optional<Category> categoryAlreadyExists = categoryRepository.findById(id);
        Category category = categoryAlreadyExists.orElseThrow(() -> new ResourceNotExists("No category was found for Id:" + id + "!"));
        return CategoryMapper.parseEntityToDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto dto) {
        Category category = categoryRepository.getReferenceById(id);
        if (Objects.isNull(category.getId())) throw new ResourceNotExists("Id not found: " + id);
        Optional<Category> categoryAlreadyExists = categoryRepository.findByName(dto.getName());
        if (categoryAlreadyExists.isPresent()) {
            throw new ResourceNotExists("This category name is already taken!");
        }
        parseDtoToEntity(category, dto);
        category = categoryRepository.save(category);
        return CategoryMapper.parseEntityToDto(category);
    }

    private void parseDtoToEntity(Category category, CategoryDto dto) {
        category.setName(dto.getName());
    }
}
