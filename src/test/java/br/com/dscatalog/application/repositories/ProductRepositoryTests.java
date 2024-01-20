package br.com.dscatalog.application.repositories;

import br.com.dscatalog.application.entities.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
 class ProductRepositoryTests {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void deleteShouldDeleteObjectWhenAValidIdIsProvided(){
        Long existingId = 4L;
        productRepository.deleteById(existingId);
        Optional<Product> product = productRepository.findById(existingId);
        Assertions.assertFalse(product.isPresent());
    }
}
