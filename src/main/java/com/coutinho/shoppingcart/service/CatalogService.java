package com.coutinho.shoppingcart.service;

import com.coutinho.shoppingcart.model.Product;
import jakarta.annotation.PostConstruct;

import java.util.Collection;

public interface CatalogService {
    @PostConstruct
    void loadInitialProducts();
    Collection<Product> getAvailableProducts();
    Product getProductByBarcode(String barcode);
}
