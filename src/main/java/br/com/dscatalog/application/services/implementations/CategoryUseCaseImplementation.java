package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.mappers.CategoryMapper;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.services.exceptions.DatabaseException;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.usecases.CategoryUseCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
    public Page<CategoryDto> findAll(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).map(CategoryMapper::parseEntityToDto);
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

    @Override
    public void delete(Long id) throws ResourceNotExists{ // We cannot use @Transactional on delete operation because we cannot get access to exception .
        try {
            var categoryAlreadyExists = categoryRepository.findById(id);
            if (categoryAlreadyExists.isEmpty()) throw new ResourceNotExists("Id not found!");
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotExists("Id not found!");
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrate Violation");
        }
    }

    private void parseDtoToEntity(Category category, CategoryDto dto) {
        category.setName(dto.getName());
    }
}
