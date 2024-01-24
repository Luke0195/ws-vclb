package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.entities.Product;
import br.com.dscatalog.application.mappers.ProductMapper;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.repositories.ProductRepository;
import br.com.dscatalog.application.services.exceptions.DatabaseException;
import br.com.dscatalog.application.services.exceptions.ResourceAlreadyExists;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.usecases.ProductUseCase;
import jakarta.persistence.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductUseCaseImplementation implements ProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductUseCaseImplementation(ProductRepository repository, CategoryRepository categoryRepository) {
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper::parseEntityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> productAlreadyExists = productRepository.findById(id);
        Product product = productAlreadyExists.orElseThrow(() -> new ResourceNotExists("Product not found!"));
        return ProductMapper.parseEntityToDto(product);
    }

    @Override
    @Transactional
    public ProductDto create(ProductDto dto) {
        Optional<Product> productAlreadyExists = productRepository.findByName(dto.getName());
        if (productAlreadyExists.isPresent()) throw new ResourceAlreadyExists("Entity already exists!");
        Product product = new Product();
        parseData(product, dto);
        product = productRepository.save(product);
        return ProductMapper.parseEntityToDto(product);
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        Product productExists = productRepository.getReferenceById(id);
        if (Objects.isNull(productExists.getId())) throw new ResourceNotExists("This entity was not found!");
        parseData(productExists, dto);
        productExists = productRepository.save(productExists);
        return ProductMapper.parseEntityToDto(productExists);
    }


    @Override
    public void delete(Long id){
        try{
            this.productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e ){
            throw new ResourceNotExists("Id not found");
        }catch (DatabaseException e){
            throw new DatabaseException("Integrate violate");
        }
    }

    private static void parseData(Product product, ProductDto dto) {
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setImgUrl(dto.getImgUrl());
        product.setPrice(dto.getPrice());
        product.setDate(dto.getDate());
        for (Category category : dto.getCategories()) {
            product.getCategories().add(new Category(category.getId(), category.getName()));
        }
    }
}
