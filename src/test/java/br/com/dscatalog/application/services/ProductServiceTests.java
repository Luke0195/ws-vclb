package br.com.dscatalog.application.services;

// Mockito vai nos ajudar a mockar os outros componentes.

import br.com.dscatalog.application.repositories.ProductRepository;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
    // Precisamos mockar de repositório que serão usados nessa classe.
    @InjectMocks
    private ProductUseCaseImplementation productUseCaseImplementation;

    private Long existingId;
    private Long nonExistingId;
    @Mock // Devemos usar o mock quando não precisamos carregar o contexto da aplicação.
    private ProductRepository productRepository;
    //@MockBean // Usamos o mock bean para quando precisamos carregar o contexto da aplicação.
    //private ProductRepository productRepository2;

    @BeforeEach
    void setup(){
        this.existingId = 1L;
        this.nonExistingId = 1000L;
        Mockito.doNothing().when(productRepository).deleteById(this.existingId);
        Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(this.nonExistingId);

    }


    @Test
    void deleteShouldDoNothingWhenIdExists(){
        Assertions.assertDoesNotThrow(() ->{
            this.productUseCaseImplementation.delete(this.existingId);
        });
        Mockito.verify(this.productRepository).deleteById(this.existingId);

    }

    @Test
     void deleteShouldThrowEmptyResultDatabaseExceptionWhenInvalidIdIsProvided(){
        Assertions.assertThrows(ResourceNotExists.class, () -> {
            productUseCaseImplementation.delete(this.nonExistingId);
        });
        Mockito.verify(this.productRepository, Mockito.times(1)).deleteById(this.nonExistingId);
    }

}
