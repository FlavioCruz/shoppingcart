# Shopping Cart User Manual

## Table of Contents
1. [Overview](#overview)
2. [System Requirements](#system-requirements)
3. [Installation](#installation)
4. [Running the Application](#running-the-application)
5. [Using the Application](#using-the-application)
6. [Product Catalog](#product-catalog)
7. [Export and Receipt Generation](#export-and-receipt-generation)
8. [Configuration](#configuration)
9. [Technical Architecture](#technical-architecture)
10. [Testing](#testing)
11. [Troubleshooting](#troubleshooting)

---

## Overview

The **Shopping Cart** application is a command-line supermarket checkout terminal built with Spring Boot. It simulates a point-of-sale system where users can:
- Browse a product catalog
- Add products to their shopping cart
- View cart contents and total cost
- Checkout and generate a JSON receipt

---

## System Requirements

- **Java**: Version 17
- **Maven**: Version 3.6+ (or use the included Maven wrapper)
- **Operating System**: Windows, macOS, or Linux
- **Disk Space**: At least 100MB for dependencies and build artifacts

---

## Installation

### Step 1: Download the Project
Ensure you have the project files in your local directory.

### Step 2: Navigate to Project Directory
```bash
cd <project-directory>
```

### Step 3: Build the Project

You can build and run in a single command using your local maven installation:
```bash
mvn clean install spring-boot:run
```
---

Using Maven wrapper (recommended):
```bash
# On Windows
mvnw.cmd clean install

# On macOS/Linux
./mvnw clean install
```

Or using your local Maven installation:
```bash
mvn clean install
```

This command will:
- Download all required dependencies
- Compile the application
- Run all unit tests
- Create a JAR file in the `target/` directory

---

## Running the Application

### Option 1: Using Maven
```bash
# Windows
mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

### Option 2: Using the JAR File
After building the project, you can run the generated JAR:
```bash
java -jar target/shoppingcart-0.0.1-SNAPSHOT.jar
```

### Option 3: Using Maven (Local Installation)
If you have Maven installed locally, you can run directly:
```bash
mvn spring-boot:run
```

### Expected Output
Upon successful startup, you'll see:
```
=============================================
SUPERMARKET CHECKOUT TERMINAL
=============================================

Select an operation:
1 - View Catalog & Add Product
2 - View Cart & Total Cost
3 - Checkout & Export Receipt (c:\temp)
0 - Cancel and Exit
Option:
```

---

## Using the Application

### Main Menu Options

The application provides an interactive command-line interface with 4 main options:

#### **Option 1: View Catalog & Add Product**
This option allows you to:
1. View the complete product catalog with barcodes, names, and prices
2. Add products to your shopping cart

**Steps:**
1. Enter `1` at the main menu
2. Browse the product catalog displayed on screen
3. Enter the desired product's barcode (e.g., `1001`)
4. Enter the quantity you wish to purchase
5. The system confirms the addition with a success message

**Example:**
```
--- PRODUCT CATALOG ---
[1001] Organic Apples (1kg) - $3.99
[1002] Whole Milk (1L) - $1.49
[1003] Whole Wheat Bread - $2.49
[1004] Free Range Eggs (Dozen) - $4.29
[1005] Ground Coffee (250g) - $6.99
-----------------------
Enter Barcode to add to cart: 1001
Enter Quantity: 2
[SUCCESS] 2x Organic Apples (1kg) added.
```

**Notes:**
- If you enter an invalid barcode, the system displays an error
- If you enter an invalid quantity (non-numeric or zero), the system displays an error
- Adding the same product multiple times accumulates the quantity

#### **Option 2: View Cart & Total Cost**
This option displays:
- All items currently in your shopping cart
- Quantity of each item
- Price per item line (quantity × unit price)
- Total cost of all items

**Example:**
```
--- YOUR SHOPPING CART ---
- [1001] Organic Apples (1kg) (x2): $7.98
- [1002] Whole Milk (1L) (x1): $1.49
- [1005] Ground Coffee (250g) (x1): $6.99
TOTAL COST: $16.46
--------------------------
```

If the cart is empty, you'll see:
```
--- YOUR SHOPPING CART ---
The cart is currently empty.
--------------------------
```

#### **Option 3: Checkout & Export Receipt**
This option:
1. Generates a JSON receipt file with all cart items
2. Saves the receipt to the configured directory (default: `c:\temp\cart.json`)
3. Exits the application

**Example Output:**
```
[SUCCESS] Receipt JSON generated at: c:\temp\cart.json
Exiting terminal...
```

**Receipt Format (cart.json):**
```json
[
  {
    "product": {
      "barcode": "1001",
      "name": "Organic Apples (1kg)",
      "price": 3.99
    },
    "quantity": 2
  },
  {
    "product": {
      "barcode": "1002",
      "name": "Whole Milk (1L)",
      "price": 1.49
    },
    "quantity": 1
  }
]
```

#### **Option 0: Cancel and Exit**
Exits the application without generating a receipt.

---

## Product Catalog

The application comes with a pre-configured product catalog:

| Barcode | Product Name | Price |
|---------|--------------|-------|
| 1001 | Organic Apples (1kg) | $3.99 |
| 1002 | Whole Milk (1L) | $1.49 |
| 1003 | Whole Wheat Bread | $2.49 |
| 1004 | Free Range Eggs (Dozen) | $4.29 |
| 1005 | Ground Coffee (250g) | $6.99 |

**Note for Developers:** The catalog is initialized in `CatalogServiceImpl.java` and can be modified by editing the `loadInitialProducts()` method or by implementing a database integration.

---

## Export and Receipt Generation

### Default Export Location
Receipts are exported to `c:\temp\cart.json` by default.

### Changing the Export Directory

You can customize the export directory in two ways:

#### Method 1: Edit application.properties
Open `src/main/resources/application.properties` and add:
```properties
export.directory=c:\\your\\custom\\path
```

**Note:** Use double backslashes (`\\`) for Windows paths or forward slashes (`/`).

#### Method 2: Use Command-Line Arguments
```bash
java -jar target/shoppingcart-0.0.1-SNAPSHOT.jar --export.directory=c:\your\custom\path
```

### Receipt File Format
The receipt is generated in JSON format containing:
- Product details (barcode, name, price)
- Quantity purchased
- Total price calculations are implicit (quantity × price)

---

## Configuration

### Application Properties
The application can be configured via `src/main/resources/application.properties`:

```properties
# Export directory for receipts (default: c:\temp)
export.directory=c:\\temp

# Spring Boot logging level (optional)
logging.level.com.coutinho.shoppingcart=INFO
```

### Build Configuration
The project uses Maven for dependency management. Key dependencies include:
- Spring Boot 4.0.3
- Jackson for JSON processing
- Lombok for reducing boilerplate code
- Spring Validation for input validation

---

## Technical Architecture

### Project Structure
```
shoppingcart/
├── src/main/java/com/coutinho/shoppingcart/
│   ├── ShoppingCartApplication.java    # Main entry point
│   ├── ShoppingCart.java               # Core shopping cart domain model
│   ├── model/
│   │   ├── Product.java                # Product entity
│   │   └── Item.java                   # Cart item entity
│   ├── service/
│   │   ├── CartService.java            # Cart operations interface
│   │   ├── CatalogService.java         # Catalog operations interface
│   │   └── impl/
│   │       ├── CartServiceImpl.java    # Cart service implementation
│   │       └── CatalogServiceImpl.java # Catalog service implementation
│   └── infrastructure/
│       └── JsonExporter.java           # JSON export functionality
└── src/test/java/                      # Unit tests
```

### Design Patterns
- **Dependency Injection**: Using Spring's @Service and @Component annotations
- **Service Layer Pattern**: Business logic separated into service interfaces and implementations
- **Repository Pattern**: Catalog uses a simple in-memory map (can be extended to database)
- **Command Pattern**: CommandLineRunner interface for interactive CLI

### Key Components

#### 1. ShoppingCart (Domain Model)
Core business logic for managing cart items and calculating totals.

#### 2. CartService
Handles cart operations including:
- Adding products to cart
- Retrieving cart items and total cost
- Checkout process

#### 3. CatalogService
Manages product catalog:
- Loading initial products
- Retrieving available products
- Finding products by barcode

#### 4. JsonExporter
Infrastructure component responsible for exporting cart data to JSON format.

---

## Testing

The project includes comprehensive unit tests for all major components.

### Running Tests
```bash
# Run all tests
mvn test

# Run with detailed output
mvn test -X
```

### Test Coverage
Tests are located in `src/test/java/com/coutinho/shoppingcart/`:
- `ShoppingCartTest.java` - Tests cart domain logic
- `CartServiceTest.java` - Tests cart service operations
- `CatalogServiceTest.java` - Tests catalog service operations

### Test Reports
After running tests, Surefire reports are generated in:
```
target/surefire-reports/
```

---

## Troubleshooting

### Common Issues and Solutions

#### 1. Application Won't Start
**Problem:** Error about Java version
```
Error: A JRE version 17 or higher is required
```
**Solution:** Install Java 17 or higher and set JAVA_HOME environment variable.

#### 2. Export Directory Error
**Problem:** "Permission denied" or "Access denied" when generating receipt
**Solution:** 
- Ensure the target directory exists and you have write permissions
- Change export directory to a location where you have write access
- Run the application with appropriate permissions

#### 3. Build Failures
**Problem:** Maven build fails with dependency errors
**Solution:**
```bash
# Clear Maven cache and rebuild
mvn clean
mvn dependency:purge-local-repository
mvn install
```

#### 4. Port Already in Use
**Problem:** If running with Spring Shell or web features (future enhancement)
**Solution:** Change the server port in application.properties:
```properties
server.port=8081
```

#### 5. Invalid Input Handling
**Problem:** Application crashes on invalid input
**Solution:** The application includes input validation. If you encounter crashes:
- Ensure you enter numeric values when prompted for quantities
- Enter valid barcodes from the displayed catalog
- Use numeric menu options (0-3)

### Getting Help

For additional support:
1. Check the logs in the console output
2. Review test cases for usage examples
3. Examine the source code documentation
4. Contact the development team

---

## Future Enhancements

Potential improvements for future versions:
- Database integration for persistent product catalog
- User authentication and multi-user support
- Discount and promotion system
- Inventory management
- Multiple payment methods
- Web-based UI interface
- Barcode scanner integration
- Receipt printing to PDF
- Transaction history

---

## License and Credits

**Project:** Shopping Cart Demo
**Version:** 0.0.1-SNAPSHOT
**Framework:** Spring Boot 4.0.3
**Build Tool:** Apache Maven
**Java Version:** 17

---

## Quick Reference Card

| Action | Command |
|--------|---------|
| Build project | `mvnw clean install` |
| Run application | `mvnw spring-boot:run` |
| Run tests | `mvn test` |
| View catalog | Enter `1` at main menu |
| View cart | Enter `2` at main menu |
| Checkout | Enter `3` at main menu |
| Exit | Enter `0` at main menu |
| Receipt location | `c:\temp\cart.json` (default) |

---

**End of User Manual**
