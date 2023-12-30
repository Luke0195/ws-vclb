package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.entities.Category;

import java.util.List;

public interface CategoryUseCases {
    CategoryDto create(CategoryDto  dto);
    CategoryDto findById(Long id);
    List<CategoryDto> findAll();
    CategoryDto updateCategory(Long id, CategoryDto dto);
}
