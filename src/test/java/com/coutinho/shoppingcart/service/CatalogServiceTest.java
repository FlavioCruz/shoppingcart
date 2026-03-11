package com.coutinho.shoppingcart.service;

import com.coutinho.shoppingcart.model.Product;
import com.coutinho.shoppingcart.service.impl.CatalogServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogServiceTest {

    private CatalogService catalogService;

    @BeforeEach
    void setUp() {
        catalogService = new CatalogServiceImpl();
        catalogService.loadInitialProducts();
    }

    @Test
    void shouldLoadInitialProductsSuccessfully() {
        assertFalse(catalogService.getAvailableProducts().isEmpty());
        assertEquals(5, catalogService.getAvailableProducts().size());
    }

    @Test
    void shouldFindProductWhenBarcodeIsValid() {
        Product product = catalogService.getProductByBarcode("1001");

        assertNotNull(product);
        assertEquals("Organic Apples (1kg)", product.getName());
    }

    @Test
    void shouldReturnNullWhenBarcodeIsInvalid() {
        Product product = catalogService.getProductByBarcode("9999");

        assertNull(product);
    }
}