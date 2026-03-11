package com.coutinho.shoppingcart.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {
    @EqualsAndHashCode.Include
    private final String barcode;
    private final String name;
    private final BigDecimal price;
}