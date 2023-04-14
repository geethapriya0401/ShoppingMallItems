# ShoppingMallItems
sqlquery for database creation

CREATE DATABASE shop_management;

USE shop_management;

CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL
);

shopitemdao.java


public class ShopItemDao 


    private final String url = "jdbc:mysql://localhost:3307/shop_management";
    private final String user = "root";
    private final String password = "admin";

    change these lines for what your database user name and password likewise 
