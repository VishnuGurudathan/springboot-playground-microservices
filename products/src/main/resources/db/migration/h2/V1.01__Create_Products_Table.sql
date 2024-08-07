-- File: src/main/resources/h2/V1.01__Create_Products_Table.sql

CREATE TABLE Products (
    product_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    price DOUBLE NOT NULL,
    category VARCHAR(255),
    stock_quantity INT NOT NULL,
    created_at TIMESTAMP,
    created_by VARCHAR(255),
    updated_at TIMESTAMP,
    updated_by VARCHAR(255)
);