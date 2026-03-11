package com.coutinho.shoppingcart.service;

import com.coutinho.shoppingcart.model.Item;
import com.coutinho.shoppingcart.model.Product;

import java.math.BigDecimal;
import java.util.Collection;

public interface CartService {

    void addProductToCart(Product product, int quantity);
    Collection<Item> getCartItems();
    BigDecimal getTotalCost();
    void checkout();
}
