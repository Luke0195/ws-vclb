package br.com.dscatalog.application.services.implementations;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.entities.Product;
import br.com.dscatalog.application.mappers.ProductMapper;
import br.com.dscatalog.application.repositories.CategoryRepository;
import br.com.dscatalog.application.repositories.ProductRepository;
import br.com.dscatalog.application.services.exceptions.ResourceNotExists;
import br.com.dscatalog.application.services.usecases.ProductUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductUseCaseImplementation implements ProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductUseCaseImplementation(ProductRepository repository, CategoryRepository categoryRepository){
        this.productRepository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(PageRequest pageRequest){
     return productRepository.findAll(pageRequest).map(ProductMapper::parseEntityToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Optional<Product> productAlreadyExists = productRepository.findById(id);
        Product product = productAlreadyExists.orElseThrow(() -> new ResourceNotExists("Product not found!"));
        return ProductMapper.parseEntityToDto(product);
    }
}
