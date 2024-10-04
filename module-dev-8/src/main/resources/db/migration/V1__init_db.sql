CREATE SEQUENCE IF NOT EXISTS customers_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS products_id_seq
    START WITH 1
    INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS orders_id_seq
    START WITH 1
    INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS customers
(
    id BIGINT PRIMARY KEY DEFAULT nextval('customers_id_seq'),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS products
(
    id BIGINT PRIMARY KEY DEFAULT nextval('products_id_seq'),
    product_name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT NOT NULL
);


CREATE TABLE IF NOT EXISTS orders
(
    id BIGINT PRIMARY KEY DEFAULT nextval('orders_id_seq'),
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL
);
