CREATE DATABASE IF NOT EXISTS shop_db;

USE shop_db;

CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    surnames VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

CREATE TABLE IF NOT EXISTS carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total DOUBLE(10, 2) NOT NULL,
    user_id INT NOT NULL,
    CONSTRAINT fk_users_carts
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(100) NOT NULL,
    price DOUBLE(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT fk_products_items
        FOREIGN KEY (product_id)
        REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS carts_items (
    cart_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (cart_id, item_id),
    CONSTRAINT fk_carts_carts_items
        FOREIGN KEY (cart_id)
        REFERENCES carts(id),
    CONSTRAINT fk_items_carts_items
        FOREIGN KEY (item_id)
        REFERENCES items(id)
);