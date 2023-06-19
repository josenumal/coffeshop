## Setting up the Database

Make sure you have PostgreSQL installed. If not, please install it first.

Once PostgreSQL is installed, create a user named "coffee_shop_dev" with the following command:

`CREATE USER coffee_shop_dev WITH PASSWORD 'coffee_shop_pwd';`

Create a database named "coffee_shop" with created user:

`CREATE DATABASE coffee_shop WITH OWNER coffee_shop_dev;`

Create The schema. On Windows, follow these steps:

1. Open the command prompt and navigate to the PostgreSQL installation directory. For example:

`C:\Program Files\PostgreSQL\15\bin>`

2. Run the following command to connect to the database using the created user:

`psql -U coffee_shop_dev coffee_shop`

3. Create a schema with the following command:

`CREATE SCHEMA AUTHORIZATION coffee_shop_dev;`

That's it! Your database setup is complete. You can now proceed with configuring your application to connect to the "
coffee_shop" database using the "coffee_shop_dev" user.

Please note that the actual installation path and version numbers may vary depending on your specific PostgreSQL
installation. Make sure to adjust the paths and commands accordingly.

