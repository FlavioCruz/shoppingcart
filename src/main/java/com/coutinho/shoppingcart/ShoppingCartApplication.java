package com.coutinho.shoppingcart;

import com.coutinho.shoppingcart.model.Product;
import com.coutinho.shoppingcart.service.CartService;
import com.coutinho.shoppingcart.service.CatalogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class ShoppingCartApplication implements CommandLineRunner {

	private final CartService cartService;
	private final CatalogService catalogService;

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Override
	public void run(String... args) {
		log.info("Supermarket Checkout Terminal started by user.");
		Scanner scanner = new Scanner(System.in);
		boolean running = true;

		System.out.println("\n=============================================");
		System.out.println("SUPERMARKET CHECKOUT TERMINAL");
		System.out.println("=============================================");

		while (running) {
			System.out.println("\nSelect an operation:");
			System.out.println("1 - View Catalog & Add Product");
			System.out.println("2 - View Cart & Total Cost");
			System.out.println("3 - Checkout & Export Receipt (c:\\temp)");
			System.out.println("0 - Cancel and Exit");
			System.out.print("Option: ");

			String option = scanner.nextLine();

			switch (option) {
				case "1":
					System.out.println("\n--- PRODUCT CATALOG ---");
					catalogService.getAvailableProducts().forEach(product ->
							System.out.printf("[%s] %s - $%.2f%n", product.getBarcode(), product.getName(), product.getPrice())
					);
					System.out.println("-----------------------");
					System.out.print("Enter Barcode to add to cart: ");
					String catalogBarcode = scanner.nextLine();

					Product catalogProduct = catalogService.getProductByBarcode(catalogBarcode);
					if (catalogProduct != null) {
						System.out.print("Enter Quantity: ");
						try {
							int qty = Integer.parseInt(scanner.nextLine());
							if (qty > 0) {
								cartService.addProductToCart(catalogProduct, qty);
								System.out.println("[SUCCESS] " + qty + "x " + catalogProduct.getName() + " added.");
							} else {
								System.out.println("[ERROR] Quantity must be greater than zero.");
							}
						} catch (NumberFormatException e) {
							System.out.println("[ERROR] Invalid quantity format.");
						}
					} else {
						System.out.println("[ERROR] Product not found in catalog.");
					}
					break;

				case "2":
					System.out.println("\n--- YOUR SHOPPING CART ---");
					if (cartService.getCartItems().isEmpty()) {
						System.out.println("The cart is currently empty.");
					} else {
						cartService.getCartItems().forEach(item ->
								System.out.printf("- [%s] %s (x%d): $%.2f%n",
										item.getProduct().getBarcode(),
										item.getProduct().getName(),
										item.getQuantity(),
										item.getTotalPrice())
						);
						System.out.printf("TOTAL COST: $%.2f%n", cartService.getTotalCost());
					}
					System.out.println("--------------------------");
					break;

				case "3":
					cartService.checkout();
					running = false;
					break;

				case "0":
					log.info("User cancelled the operation and exited.");
					System.out.println("Exiting terminal...");
					running = false;
					break;

				default:
					System.out.println("[ERROR] Invalid option.");
			}
		}
		scanner.close();
		log.info("Application shutdown gracefully.");
	}
}