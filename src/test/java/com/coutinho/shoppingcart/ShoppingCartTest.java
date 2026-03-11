package com.coutinho.shoppingcart;

import static org.junit.jupiter.api.Assertions.*;

import com.coutinho.shoppingcart.model.Item;
import com.coutinho.shoppingcart.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class ShoppingCartTest {

    private ShoppingCart cart;
    private Product apple;
    private Product milk;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
        apple = new Product("1001", "Apple", new BigDecimal("3.00"));
        milk = new Product("1002", "Milk", new BigDecimal("2.50"));
    }

    @Test
    void shouldAddNewProductToCart() {
        cart.addItem(apple, 2);

        assertEquals(1, cart.getItems().size());
        assertEquals(new BigDecimal("6.00"), cart.calculateTotal());
    }

    @Test
    void shouldIncrementQuantityWhenProductAlreadyExists() {
        cart.addItem(apple, 2);
        cart.addItem(apple, 3);

        assertEquals(1, cart.getItems().size());

        Item item = cart.getItems().iterator().next();
        assertEquals(5, item.getQuantity());
        assertEquals(new BigDecimal("15.00"), cart.calculateTotal());
    }

    @Test
    void shouldCalculateCorrectTotalForMultipleProducts() {
        cart.addItem(apple, 2);
        cart.addItem(milk, 1);

        assertEquals(2, cart.getItems().size());
        assertEquals(new BigDecimal("8.50"), cart.calculateTotal());
    }

    @Test
    void shouldReturnTotalZeroWhenCartIsEmpty() {
        ShoppingCart emptyCart = new ShoppingCart();
        assertEquals(new BigDecimal("0"), emptyCart.calculateTotal());
        assertTrue(emptyCart.getItems().isEmpty());
    }

    @Test
    void shouldHandleLargeQuantitiesCorrectly() {
        cart.addItem(apple, 1000);

        assertEquals(new BigDecimal("3000.00"), cart.calculateTotal());
    }
}