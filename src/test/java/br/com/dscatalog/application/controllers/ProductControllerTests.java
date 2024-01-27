package br.com.dscatalog.application.controllers;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.factories.ProductFactory;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.implementations.ProductUseCaseImplementation;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    private Long nonExistingId;
    private long existingId;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        this.nonExistingId = 1000000L;
        this.existingId = 1L;
        this.productDto = new ProductDto(ProductFactory.makeNotExistingProduct(1L));
        this.pageDto = new PageImpl<>(List.of(this.productDto));
        Mockito.when(productUseCaseImplementation.findAllPaged(ArgumentMatchers.any())).thenReturn(pageDto);
        Mockito.when(productUseCaseImplementation.findById(this.nonExistingId)).thenThrow(ResourceNotExists.class);
        Mockito.when(productUseCaseImplementation.findById(this.existingId)).thenReturn(productDto);
        Mockito.when(productUseCaseImplementation.update(this.nonExistingId, productDto)).thenThrow(ResourceNotExists.class);
        Mockito.doThrow(ResourceNotExists.class).when(productUseCaseImplementation).delete(this.nonExistingId);
        Mockito.when(productUseCaseImplementation.update(this.existingId, productDto)).thenReturn(this.productDto);


    }

    @Test
    void findAllShouldReturnPage() throws Exception {
        ResultActions result = mockMvc.perform(get("/products").accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnNotFoundWhenAnInvalidIdIsProvided() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", this.nonExistingId));
        result.andExpect(status().isNotFound());
    }

    @Test
    void findByIdShouldReturnAProductWhenValidIdIsProvided() throws Exception {
        ResultActions result = mockMvc.perform(get("/products/{id}", this.existingId).accept(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk());
    }

    @Test
    void updateShouldThrowResourceNotExistsWhenIdDoesNotExists() throws Exception {
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", this.nonExistingId));
        resultActions.andExpect(status().isBadRequest());
    }

    @Test
    void updateShouldReturnProductDtoWhenExists() throws Exception{
        String jsonBody = objectMapper.writeValueAsString(this.productDto);
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.put("/products/{id}", existingId)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());

    }
    @Test
    void deleteShouldDoNothingWhenValidIdIsProvided() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", this.existingId));
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    void deleteShouldReturnNotFoundWhenInvalidIdIsProvided() throws Exception{
        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.delete("/products/{id}", this.nonExistingId));
        resultActions.andExpect(status().isNotFound());
    }
}
