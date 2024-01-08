package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.dtos.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
public interface CategoryUseCases {
    CategoryDto create(CategoryDto dto);
    CategoryDto findById(Long id);
    Page<CategoryDto> findAll(PageRequest pageRequest);
    CategoryDto updateCategory(Long id, CategoryDto dto);
    void delete(Long id);
}
