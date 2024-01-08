package br.com.dscatalog.application.mappers;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.entities.Category;

public class CategoryMapper {
    private CategoryMapper() {
    }

    public static CategoryDto parseEntityToDto(Category entity) {
        CategoryDto dto = new CategoryDto();
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    public static Category parseDtoToCategory(CategoryDto dto) {
        Category category = new Category();
        category.setName(dto.getName());
        return category;
    }
}
