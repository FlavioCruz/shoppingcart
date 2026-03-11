package com.coutinho.shoppingcart.service.impl;

import com.coutinho.shoppingcart.model.Product;
import com.coutinho.shoppingcart.service.CatalogService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CatalogServiceImpl implements CatalogService {

    private final Map<String, Product> catalog = new HashMap<>();

    @Override
    @PostConstruct
    public void loadInitialProducts() {
        catalog.put("1001", new Product("1001", "Organic Apples (1kg)", new BigDecimal("3.99")));
        catalog.put("1002", new Product("1002", "Whole Milk (1L)", new BigDecimal("1.49")));
        catalog.put("1003", new Product("1003", "Whole Wheat Bread", new BigDecimal("2.49")));
        catalog.put("1004", new Product("1004", "Free Range Eggs (Dozen)", new BigDecimal("4.29")));
        catalog.put("1005", new Product("1005", "Ground Coffee (250g)", new BigDecimal("6.99")));

        log.info("Product catalog initialized with {} items.", catalog.size());
    }

    @Override
    public Collection<Product> getAvailableProducts() {
        return catalog.values();
    }

    @Override
    public Product getProductByBarcode(String barcode) {
        return catalog.get(barcode);
    }
}
