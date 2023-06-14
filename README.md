CREATE USER coffee_shop_dev WITH PASSWORD 'coffee_shop_pwd';

CREATE DATABASE coffee_shop WITH OWNER coffee_shop_dev;

C:\Program Files\PostgreSQL\15\bin>psql coffee_shop_dev

CREATE SCHEMA AUTHORIZATION coffee_shop_dev;