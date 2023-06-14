CREATE TABLE coffee_shop_dev.products (
	product_id SMALLSERIAL PRIMARY KEY,
	product_name VARCHAR ( 80 ) UNIQUE NOT NULL,
	price NUMERIC (5, 2) NOT NULL,
	available BOOLEAN NOT NULL DEFAULT TRUE
);