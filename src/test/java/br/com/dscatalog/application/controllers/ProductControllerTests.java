package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.factories.ProductFactory;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Usamos o mockBean quando testamos componentes da classes de controladores.
    private ProductUseCaseImplementation productUseCaseImplementation;

    private PageImpl<ProductDto> pageDto;
    private ProductDto productDto;
    @BeforeEach
    void setup(){
        this.productDto = new ProductDto(ProductFactory.makeProductWithNoArguments());
        this.pageDto = new PageImpl<>(List.of(this.productDto));
        Mockito.when(productUseCaseImplementation.findAllPaged(ArgumentMatchers.any())).thenReturn(pageDto);
    }

    @Test
    void findAllShouldReturnPage() throws Exception{
        ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }


}
