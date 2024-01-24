package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.Product;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.math.BigDecimal;
import java.util.Optional;


@DataJpaTest
 class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() throws Exception{
        this.existingId = 1L;
        this.nonExistingId = 10000L;

    }

   @Test
   void deleteShouldDeleteAnProductWhenAValidaIdIsProvided(){
        Long existingId = 1L;
        productRepository.deleteById(existingId);
       Optional<Product> productExists = productRepository.findById(existingId);
       Assertions.assertFalse(productExists.isPresent());
   }

   /*
   @Test
    void deleteShouldThrowResourceNotFoundExceptionWhenAnInvalidIdIsProvided(){
     Assertions.assertThrows(ResourceNotExists.class, () -> productRepository.deleteById(1000L));
   }
    */


    @Test
    void saveShouldPersistAnProductWithAutoIncrement(){
        Product entity = new Product();
        entity.setId(8L);
        entity.setName("any_name");
        entity.setDescription("any_description");
        entity.setImgUrl("any_Image");
        entity.setPrice(BigDecimal.valueOf(90.30));
        entity = productRepository.save(entity);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(8, entity.getId());
    }

    @Test
    void findByIdShouldReturnAnProductWhenAValidIdIsProvided(){
        Optional<Product> productAlreadyExists = productRepository.findById(this.existingId);
        Assertions.assertTrue(productAlreadyExists.isPresent());
    }

    @Test
    void findByIdShoudReturnEmptyWhenAProductIdWasNotFound(){
        Optional<Product> product = productRepository.findById(10000L);
        Assertions.assertFalse(product.isPresent());
    }
}
