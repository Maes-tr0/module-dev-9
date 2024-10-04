package ua.maestr0.crud.util;

import ua.maestr0.crud.anotation.Id;
import ua.maestr0.crud.model.Customer;
import ua.maestr0.crud.model.Order;
import ua.maestr0.crud.model.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

public class Validator {
    private static final String REGEX_FIO = "^[A-Za-z\\s'-]{2,50}$";
    private static final String REGEX_EMAIL = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String REGEX_PHONE = "^\\+?\\d{10,15}$";
    private static final String REGEX_PRODUCT_NAME = "^[A-Za-z0-9\\s'-]{2,100}$";
    private static final Set<String> VALID_ORDER_STATUSES = Set.of("Pending", "Processing", "Shipped", "Delivered", "Cancelled");

    private Validator() {}

    public static <T> void validateEntity(T entity) {
        checkNullFields(entity);

        if (entity.getClass() == Customer.class) {
            validateCustomer((Customer) entity);
        } else if (entity.getClass() == Product.class) {
            validateProduct((Product) entity);
        } else if (entity.getClass() == Order.class) {
            validateOrder((Order) entity);
        } else {
            throw new IllegalArgumentException("Unsupported entity type: " + entity.getClass().getSimpleName());
        }
    }

    private static <T> void checkNullFields(T entity) {
        Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .peek(field -> field.setAccessible(true))
                .forEach(field -> {
                    try {
                        Object value = field.get(entity);
                        if (value == null) {
                            throw new IllegalArgumentException(field.getName() + " cannot be null.");
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Unable to access field: " + field.getName(), e);
                    } finally {
                        field.setAccessible(false);
                    }
                });
    }


    private static void validateCustomer(Customer customer) {
        if (!customer.getFirstName().matches(REGEX_FIO)) {
            throw new IllegalArgumentException("First name must be between 2 and 50 characters and contain only letters, spaces, hyphens, or apostrophes.");
        }
        if (!customer.getLastName().matches(REGEX_FIO)) {
            throw new IllegalArgumentException("Last name must be between 2 and 50 characters and contain only letters, spaces, hyphens, or apostrophes.");
        }
        if (!customer.getEmail().matches(REGEX_EMAIL)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        if (!customer.getPhone().matches(REGEX_PHONE)) {
            throw new IllegalArgumentException("Invalid phone number format. It should contain 10 to 15 digits and may start with '+'.");
        }
    }

    private static void validateProduct(Product product) {
        if (!product.getProductName().matches(REGEX_PRODUCT_NAME)) {
            throw new IllegalArgumentException("Product name must be between 2 and 100 characters and contain only letters, numbers, spaces, hyphens, or apostrophes.");
        }
        if (product.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be a positive number.");
        }
        if (product.getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative.");
        }
    }

    private static void validateOrder(Order order) {
        if (order.getOrderDate().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Order date cannot be in the future.");
        }
        if (order.getTotalAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Total amount must be a positive number.");
        }
    }
}
