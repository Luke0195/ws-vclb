package br.com.dscatalog.application.factories;

import br.com.dscatalog.application.entities.Product;

import java.math.BigDecimal;

public class ProductFactory {

    private ProductFactory(){};


    public static Product makeProductWithNoArguments(){
        return new Product();
    }

    public static Product makeNotExistingProduct(Long id){
        return new Product(id,"any_name", "any_description", BigDecimal.valueOf(30.50), null);
    }
}
