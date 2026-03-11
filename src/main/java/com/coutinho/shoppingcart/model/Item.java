package com.coutinho.shoppingcart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item {
    private final Product product;
    private int quantity;

    public BigDecimal getTotalPrice() {
        return product.getPrice().multiply(new BigDecimal(quantity));
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }
}