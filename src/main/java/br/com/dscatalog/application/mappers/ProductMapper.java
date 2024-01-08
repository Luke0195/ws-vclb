package br.com.dscatalog.application.mappers;

import br.com.dscatalog.application.dtos.ProductDto;
import br.com.dscatalog.application.entities.Category;
import br.com.dscatalog.application.entities.Product;

import java.util.List;

public class ProductMapper {

    private ProductMapper(){}

    public static ProductDto parseEntityToDto(Product product, List<Category> categoryList){
        var dto = new ProductDto();
        setData(dto, product);
        for(Category cat: categoryList){
            dto.getCategories().add( new Category(cat.getId(), cat.getName()));
        }
        return dto;
    }
    public static ProductDto parseEntityToDto(Product product){
        var  dto = new ProductDto();
        setData(dto, product);
        return dto;
    }

    private static void setData(ProductDto dto, Product product){
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDate(product.getDate());
        dto.setDescription(product.getDescription());
        dto.setImgUrl(product.getImgUrl());
        dto.setCreatedAt(product.getCreatedAt());
        dto.setUpdateAt(product.getUpdatedAt());
        product.getCategories().forEach(x -> dto.getCategories().add( new Category(x.getId(), x.getName())));
    }
}
