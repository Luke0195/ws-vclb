package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductUseCase {
    Page<ProductDto> findAllPaged(Pageable pageable);

    ProductDto findById(Long id);

    ProductDto create(ProductDto dto);

    ProductDto update(Long id, ProductDto dto);

    void delete(Long id);
}
