package ua.maestr0.crud;

import ua.maestr0.crud.dto.ServiceDto;
import ua.maestr0.crud.model.Customer;
import ua.maestr0.crud.model.Order;
import ua.maestr0.crud.model.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args) {
        ServiceDto serviceDto = new ServiceDto();

        //customerServiceDTOTest(serviceDto);
        //orderServiceDTOTest(serviceDto);
        //productServiceDTOTest(serviceDto);
    }

    private static void productServiceDTOTest(ServiceDto serviceDto) {
        // 1. Створення нового продукту
        Product newProduct = new Product(
                "Wireless Charger",
                "Fast wireless charging pad",
                BigDecimal.valueOf(29.99),
                120);
        boolean isCreated = serviceDto.create(newProduct);
        System.out.println("Product created: " + isCreated);

        // 2. Пошук продукту за ID
        Product foundProduct = serviceDto.findById(1L, Product.class);
        System.out.println("Product found by ID: " + foundProduct);

        // 3. Отримання всіх продуктів
        List<Product> allProducts = serviceDto.findAll(Product.class);
        System.out.println("All products: ");
        allProducts.forEach(System.out::println);

        // 4. Оновлення продукту
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("price", BigDecimal.valueOf(899.99)); // Оновлюємо ціну
        boolean isUpdated = serviceDto.update(1L, updateFields, Product.class);
        System.out.println("Product updated: " + isUpdated);

        // 5. Перевірка оновлення
        Product updatedProduct = serviceDto.findById(1L, Product.class);
        System.out.println("Updated product: " + updatedProduct);

        // 6. Видалення продукту
        boolean isDeleted = serviceDto.delete(1L, Product.class);
        System.out.println("Product deleted: " + isDeleted);
    }

    private static void orderServiceDTOTest(ServiceDto serviceDto) {
        // 1. Створення нового замовлення
        Order newOrder = new Order(
                LocalDateTime.now(),
                BigDecimal.valueOf(899.99),
                "In Progress");
        boolean isCreated = serviceDto.create(newOrder);
        System.out.println("Order created: " + isCreated);

        // 2. Пошук замовлення за ID
        Order foundOrder = serviceDto.findById(1L, Order.class);
        System.out.println("Order found by ID: " + foundOrder);

        // 3. Отримання всіх замовлень
        List<Order> allOrders = serviceDto.findAll(Order.class);
        System.out.println("All orders: ");
        allOrders.forEach(System.out::println);

        // 4. Оновлення замовлення
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("status", "Shipped"); // Оновлюємо статус замовлення
        boolean isUpdated = serviceDto.update(1L, updateFields, Order.class);
        System.out.println("Order updated: " + isUpdated);

        // 5. Перевірка оновлення
        Order updatedOrder = serviceDto.findById(1L, Order.class);
        System.out.println("Updated order: " + updatedOrder);

        // 6. Видалення замовлення
        boolean isDeleted = serviceDto.delete(1L, Order.class);
        System.out.println("Order deleted: " + isDeleted);
    }

    private static void customerServiceDTOTest(ServiceDto serviceDto) {
        // 1. Створення нового запису в базі даних
        Customer newCustomer = new Customer(
                "Oleksandr",
                "Shevchenko",
                "oleksandr.shevchenko@example.com",
                "+380991234567");
        boolean isCreated = serviceDto.create(newCustomer);
        System.out.println("Customer created: " + isCreated);

        // 2. Пошук за ID
        Customer foundCustomer = serviceDto.findById(1L, Customer.class);
        System.out.println("Customer found by ID: " + foundCustomer);

        // 3. Отримання всіх записів
        List<Customer> allCustomers = serviceDto.findAll(Customer.class);
        System.out.println("All customers: ");
        allCustomers.forEach(System.out::println);

        // 4. Оновлення запису
        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("firstName", "Jonathan"); // Оновлюємо ім'я
        boolean isUpdated = serviceDto.update(1L, updateFields, Customer.class);
        System.out.println("Customer updated: " + isUpdated);

        // 5. Перевірка, чи оновлення успішне
        Customer updatedCustomer = serviceDto.findById(1L, Customer.class);
        System.out.println("Updated customer: " + updatedCustomer);

        // 6. Видалення запису
        boolean isDeleted = serviceDto.delete(1L, Customer.class);
        System.out.println("Customer deleted: " + isDeleted);
    }
}
