
-- V1__init_schema.sql

-- Create Users table
CREATE TABLE set_user (
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    hash_password VARCHAR(255) NOT NULL
);

-- Create Shopping List table
CREATE TABLE set_shopping_list (
    shopping_list_id SERIAL PRIMARY KEY,
    list_name VARCHAR(255),
    total NUMERIC(10, 2),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES set_user(user_id) ON DELETE CASCADE
);

-- Create Shopping List Items table
CREATE TABLE set_shopping_list_item (
    shopping_list_item_id SERIAL PRIMARY KEY,
    shopping_list_id INTEGER NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INTEGER DEFAULT 1,
    unit_price NUMERIC(10, 2),
    checked_out BOOLEAN DEFAULT FALSE,
    total NUMERIC(10, 2),
    FOREIGN KEY (shopping_list_id) REFERENCES set_shopping_list(shopping_list_id) ON DELETE CASCADE
);
