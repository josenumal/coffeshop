create table coffee_shop_dev.product (
	product_id smallserial primary key,
	product_name varchar ( 80 ) unique not null,
	price numeric (5, 2) not null,
	available boolean not null default true
);