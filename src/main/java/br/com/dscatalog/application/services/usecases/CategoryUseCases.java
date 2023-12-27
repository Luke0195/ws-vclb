package br.com.dscatalog.application.services.usecases;

import br.com.dscatalog.application.entities.Category;

import java.util.List;

public interface CategoryUseCases {
    Category create(Category  category);
    List<Category> findAll();
}
