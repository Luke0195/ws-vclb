package br.com.dscatalog.application.factories;

import br.com.dscatalog.application.entities.Category;

public class CategoryFactory {

    public static Category makeCategoryWithoutParams(Long id, String name){
        return new Category(1L, "any_category_name");
    }

    public static Category makeCategoryWithParams(Long id, String name){
        return new Category(id, name);
    }
}
