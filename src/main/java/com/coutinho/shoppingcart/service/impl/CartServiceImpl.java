package com.coutinho.shoppingcart.service.impl;

import com.coutinho.shoppingcart.ShoppingCart;
import com.coutinho.shoppingcart.infrastructure.JsonExporter;
import com.coutinho.shoppingcart.model.Product;
import com.coutinho.shoppingcart.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.coutinho.shoppingcart.model.Item;

import java.math.BigDecimal;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ShoppingCart cart = new ShoppingCart();
    private final JsonExporter jsonExporter;

    public void addProductToCart(Product product, int quantity) {
        cart.addItem(product, quantity);
        log.info("Added {}x of product {} to cart.", quantity, product.getBarcode());
    }

    public Collection<Item> getCartItems() {
        return cart.getItems();
    }

    public BigDecimal getTotalCost() {
        return cart.calculateTotal();
    }

    public void checkout() {
        log.info("Initiating checkout and export process...");
        jsonExporter.exportToJson(cart);
    }
}
