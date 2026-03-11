package com.coutinho.shoppingcart.infrastructure;

import com.coutinho.shoppingcart.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class JsonExporter {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${export.directory:c:\\temp}")
    private String directory;

    public void exportToJson(ShoppingCart cart) {
        File file = new File(directory, "cart.json");

        try {
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(file, cart.getItems());

            log.info("SUCCESS: Receipt JSON generated at: {}", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("ERROR saving JSON file: {}", e.getMessage());
        }
    }
}