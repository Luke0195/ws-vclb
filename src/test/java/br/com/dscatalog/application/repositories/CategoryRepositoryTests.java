package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.factories.CategoryFactory;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    private  Long existingId;
    private  String categoryName;


    @BeforeEach
    void setup(){
        this.existingId = 1L;
        this.categoryName= "any_name";
    }



    @Test
    void findAllPagedShouldGetAllUsers(){
        Pageable pageable = PageRequest.of(0,12, Sort.Direction.valueOf("ASC"), "name");
        Page<Category> entities = categoryRepository.findAll(pageable);
        Assertions.assertNotNull(entities);
    }

    @Test
    void createShouldCreateACategoryWhenValidDataIsProvided(){
        Long expectedId = 4L;
        String expectedName = "any_name";
        Category category = CategoryFactory.makeCategoryWithParams(4L, "any_name");
        Assertions.assertNotNull(category);
        Assertions.assertEquals(expectedId, category.getId());
        Assertions.assertEquals(expectedName, category.getName());
    }

    @Test
    void findCategoryByIdShouldReturnACategoryWhenAValidIdIsProvided(){
        Optional<Category> category = categoryRepository.findById(this.existingId);
        Assertions.assertTrue(category.isPresent());
    }

    @Test
    void updateCategoryShouldUpdateACategoryWhenAValidDataIsProvided(){
        Category category = categoryRepository.getReferenceById(this.existingId);
        category.setName("any_name");
        category = categoryRepository.save(category);
        Assertions.assertEquals(this.categoryName, category.getName());
    }


}
