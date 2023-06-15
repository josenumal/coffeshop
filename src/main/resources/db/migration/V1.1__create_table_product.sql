CREATE TABLE coffee_shop_dev.product (
	id SMALLSERIAL PRIMARY KEY,
	product_name VARCHAR ( 80 ) UNIQUE NOT NULL,
	product_type VARCHAR ( 10 ) CHECK(product_type IN ('FOOD','DRINKS')),
	price NUMERIC (5, 2) NOT NULL CHECK (price > 0),
	available BOOLEAN NOT NULL DEFAULT TRUE
);