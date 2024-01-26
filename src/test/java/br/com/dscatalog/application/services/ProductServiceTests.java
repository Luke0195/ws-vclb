package br.com.dscatalog.application.services;

// Mockito vai nos ajudar a mockar os outros componentes.

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.entities.Product;
import br.com.dscatalog.application.factories.ProductFactory;
import br.com.dscatalog.application.repositories.ProductRepository;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
 class ProductServiceTests {
    // Precisamos mockar de repositório que serão usados nessa classe.
    @InjectMocks
    private ProductUseCaseImplementation productUseCaseImplementation;
    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Product product;
    private PageImpl<Product> page;

    @Mock // Devemos usar o mock quando não precisamos carregar o contexto da aplicação.
    private ProductRepository productRepository;
    //@MockBean // Usamos o mock bean para quando precisamos carregar o contexto da aplicação.
    //private ProductRepository productRepository2;
    @BeforeEach
    void setup(){
        this.existingId = 1L;
        this.nonExistingId = 1000L;
        this.dependentId = 4L;
        this.product = ProductFactory.makeProductWithNoArguments();
        this.page = new PageImpl<>(List.of(product));
        Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
        Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(this.product));
        Mockito.when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());
        Mockito.when(productRepository.save(this.product)).thenReturn(this.product);
        Mockito.doNothing().when(productRepository).deleteById(this.existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(this.nonExistingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(this.dependentId);


    }
    @Test
    void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() ->{
            this.productUseCaseImplementation.delete(this.existingId);
        });
        Mockito.verify(this.productRepository, Mockito.times(1)).deleteById(this.existingId);
    }
    @Test
     void deleteShouldThrowEmptyResultDatabaseExceptionWhenInvalidIdIsProvided(){
        Assertions.assertThrows(ResourceNotExists.class, () -> {
            productUseCaseImplementation.delete(this.nonExistingId);
        });
        Mockito.verify(this.productRepository, Mockito.times(1)).deleteById(this.nonExistingId);
    }
    @Test
    void deleteShouldThrowDataIntegrityViolationWhenAnResourceHasDependencies(){
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            productUseCaseImplementation.delete(this.dependentId);
        });
        Mockito.verify(this.productRepository).deleteById(this.dependentId);
    }


    @Test
    void findByIdShouldThrowResourceNotExistsWhenAnInvalidIdIsProvided(){
        Assertions.assertThrows(ResourceNotExists.class, () -> {
            productUseCaseImplementation.findById(nonExistingId);
        });
        productRepository.findById(nonExistingId);
    }

    @Test
    void findByIdShouldReturnAProductWhenAValidIdIsProvided(){
        ProductDto dto = productUseCaseImplementation.findById(existingId);
        Assertions.assertNotNull(dto);
    }

    @Test
    void findAllPageShouldReturnPage(){
        Pageable pageable = PageRequest.of(0,8);
        Page<ProductDto> products = productUseCaseImplementation.findAllPaged(pageable);
        Assertions.assertNotNull(products);
        Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
    }


}
