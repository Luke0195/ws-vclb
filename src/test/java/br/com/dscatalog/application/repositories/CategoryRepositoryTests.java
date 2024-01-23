package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@DataJpaTest
class CategoryRepositoryTests {

    @Autowired
    private CategoryRepository categoryRepository;


    @Test
    void findAllPagedShouldGetAllUsers(){
        Pageable pageable = PageRequest.of(0,12, Sort.Direction.valueOf("ASC"), "name");
        Page<Category> entities = categoryRepository.findAll(pageable);
        Assertions.assertNotNull(entities);
    }

    @Test
    void createShouldCreateACategoryWhenValidDataIsProvided(){
        Long createdId = 4L;
        String createdName = "any_name";
        Category category = new Category(createdId, createdName);
        Assertions.assertNotNull(category);
        Assertions.assertEquals(createdId, category.getId());
        Assertions.assertEquals(createdName, category.getName());
    }

    @Test
    void findCategoryByIdShouldReturnACategoryWhenAValidIdIsProvided(){
        Long existingId = 1L;
        Optional<Category> category = categoryRepository.findById(existingId);
        Assertions.assertTrue(category.isPresent());
    }

    @Test
    void findCategoryByIdShouldThrowsResourceNotExistsWhenAnInvalidIdIsProvided(){
        Long nonExistingId = 8L;
        Assertions.assertThrows(ResourceNotExists.class, () -> {
            categoryRepository.findById(nonExistingId);
        });
    }

}
