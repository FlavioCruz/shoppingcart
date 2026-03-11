package com.coutinho.shoppingcart.service;

import com.coutinho.shoppingcart.infrastructure.JsonExporter;
import com.coutinho.shoppingcart.model.Product;
import com.coutinho.shoppingcart.service.impl.CartServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private JsonExporter jsonExporterMock;

    @InjectMocks
    private CartServiceImpl cartService;

    @Test
    void shouldCallJsonExporterOnCheckout() {
        Product apple = new Product("1001", "Apple", new BigDecimal("3.00"));
        cartService.addProductToCart(apple, 1);

        cartService.checkout();

        verify(jsonExporterMock, times(1)).exportToJson(any());
    }
}