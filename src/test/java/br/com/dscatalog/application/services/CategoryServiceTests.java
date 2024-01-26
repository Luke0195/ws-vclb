package br.com.dscatalog.application.services;

import br.com.dscatalog.application.dtos.CategoryDto;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.implementations.CategoryUseCaseImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CategoryServiceTests {

    @InjectMocks
    private CategoryUseCaseImplementation categoryUseCaseImplementation;
    @Mock
    private CategoryRepository categoryRepository;
    private String existingName = "books";
    @BeforeEach
    void setup(){

        Mockito.doThrow(ResourceAlreadyExists.class).when(categoryRepository).findByName(this.existingName);
    }
    @Test
    void createShouldThrowResourceAlreadyExistsWhenACategoryNameAlreadyExists(){
        CategoryDto dto = new CategoryDto(1L, this.existingName);
        Assertions.assertThrows(ResourceAlreadyExists.class, () -> {
            categoryUseCaseImplementation.create(dto);
        });
        Mockito.verify(categoryRepository).findByName(dto.getName());
    }
}
