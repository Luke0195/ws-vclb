package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.dtos.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductUseCase {
    Page<ProductDto> findAllPaged(PageRequest pageRequest);
    ProductDto findById(Long id);
}
