INSERT INTO customers (first_name, last_name, email, phone)
VALUES ('John', 'Smith', 'john.smith@example.com', '+1234567890'),
       ('Jane', 'Doe', 'jane.doe@example.com', '+1987654321'),
       ('Michael', 'Brown', 'michael.brown@example.com', '+1123456789'),
       ('Emily', 'Davis', 'emily.davis@example.com', '+1212345678'),
       ('William', 'Johnson', 'william.johnson@example.com', '+1312345678'),
       ('Olivia', 'Wilson', 'olivia.wilson@example.com', '+1412345678'),
       ('James', 'Taylor', 'james.taylor@example.com', '+1512345678'),
       ('Sophia', 'Anderson', 'sophia.anderson@example.com', '+1612345678'),
       ('Robert', 'Thomas', 'robert.thomas@example.com', '+1712345678'),
       ('Isabella', 'Jackson', 'isabella.jackson@example.com', '+1812345678'),
       ('David', 'White', 'david.white@example.com', '+1912345678'),
       ('Mia', 'Harris', 'mia.harris@example.com', '+2012345678'),
       ('Joseph', 'Martin', 'joseph.martin@example.com', '+2112345678'),
       ('Charlotte', 'Thompson', 'charlotte.thompson@example.com', '+2212345678'),
       ('Thomas', 'Garcia', 'thomas.garcia@example.com', '+2312345678'),
       ('Amelia', 'Martinez', 'amelia.martinez@example.com', '+2412345678'),
       ('Charles', 'Robinson', 'charles.robinson@example.com', '+2512345678'),
       ('Harper', 'Clark', 'harper.clark@example.com', '+2612345678'),
       ('Daniel', 'Rodriguez', 'daniel.rodriguez@example.com', '+2712345678'),
       ('Evelyn', 'Lewis', 'evelyn.lewis@example.com', '+2812345678');

INSERT INTO products (product_name, description, price, stock_quantity)
VALUES
    ('Laptop', 'High performance laptop', 999.99, 50),
    ('Smartphone', 'Latest model smartphone', 699.99, 200),
    ('Tablet', 'Portable tablet with stylus support', 499.99, 100),
    ('Headphones', 'Noise-cancelling over-ear headphones', 199.99, 150),
    ('Smartwatch', 'Fitness and activity tracking smartwatch', 299.99, 75),
    ('Gaming Console', 'Next-gen gaming console', 499.99, 40),
    ('External Hard Drive', '1TB external hard drive', 79.99, 300),
    ('Monitor', '24-inch Full HD monitor', 149.99, 80),
    ('Keyboard', 'Mechanical keyboard with RGB backlight', 89.99, 120),
    ('Mouse', 'Wireless ergonomic mouse', 49.99, 180);


INSERT INTO orders (order_date, total_amount, status)
VALUES
    ('2024-10-01 10:30:00', 1499.97, 'Pending'),
    ('2024-10-01 11:45:00', 699.99, 'Shipped'),
    ('2024-10-02 09:15:00', 299.99, 'Delivered'),
    ('2024-10-02 14:20:00', 499.99, 'Processing'),
    ('2024-10-03 12:00:00', 1199.98, 'Pending'),
    ('2024-10-03 16:45:00', 249.98, 'Delivered'),
    ('2024-10-04 08:10:00', 79.99, 'Shipped'),
    ('2024-10-04 13:30:00', 599.99, 'Processing'),
    ('2024-10-05 15:25:00', 199.99, 'Delivered'),
    ('2024-10-05 17:50:00', 49.99, 'Shipped');
