package br.com.dscatalog.application.factories;

import br.com.dscatalog.application.entities.Product;

import java.math.BigDecimal;

public class ProductFactory {

    private ProductFactory(){};


    public static Product makeProductWithNoArguments(){
        return new Product(1L, "any_name",  "any_description", BigDecimal.valueOf(31.50), null);
    }

}
