package com.coutinho.shoppingcart;

import com.coutinho.shoppingcart.model.Item;

import com.coutinho.shoppingcart.model.Product;
import java.math.BigDecimal;
import java.util.*;

public class ShoppingCart {

    private final Map<String, Item> items = new HashMap<>();

    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getBarcode())) {
            items.get(product.getBarcode()).addQuantity(quantity);
        } else {
            items.put(product.getBarcode(), new Item(product, quantity));
        }
    }

    public Collection<Item> getItems() {
        return items.values();
    }

    public BigDecimal calculateTotal() {
        return items.values().stream()
                .map(Item::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}