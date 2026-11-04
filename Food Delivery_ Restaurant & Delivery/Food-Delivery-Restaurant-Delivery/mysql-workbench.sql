CREATE DATABASE IF NOT EXISTS food_delivery_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE food_delivery_db;

CREATE TABLE IF NOT EXISTS restaurant (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    phone VARCHAR(255)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS meal (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price DECIMAL(10,2) NOT NULL,
    restaurant_id BIGINT NOT NULL,
    CONSTRAINT fk_meal_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant(id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS food_orders (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    delivery_address VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    meal_id BIGINT NOT NULL,
    CONSTRAINT fk_order_meal FOREIGN KEY (meal_id) REFERENCES meal(id)
) ENGINE=InnoDB;

INSERT INTO restaurant (name, address, phone)
SELECT 'Pizza House', 'Zenica, Titova 12', '061-123-456'
WHERE NOT EXISTS (SELECT 1 FROM restaurant WHERE name = 'Pizza House' AND address = 'Zenica, Titova 12');

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Margherita', 'Paradajz, mozzarella i bosiljak', 10.00, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Pizza House' AND address = 'Zenica, Titova 12')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Margherita' AND restaurant_id = restaurant.id);

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Capricciosa', 'Sir, sunka i gljive', 13.50, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Pizza House' AND address = 'Zenica, Titova 12')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Capricciosa' AND restaurant_id = restaurant.id);

INSERT INTO restaurant (name, address, phone)
SELECT 'Burger Corner', 'Sarajevo, Zmaja od Bosne 8', '062-555-444'
WHERE NOT EXISTS (SELECT 1 FROM restaurant WHERE name = 'Burger Corner' AND address = 'Sarajevo, Zmaja od Bosne 8');

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Classic burger', 'Junetina, salata i domaci sos', 9.00, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Burger Corner' AND address = 'Sarajevo, Zmaja od Bosne 8')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Classic burger' AND restaurant_id = restaurant.id);

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Cheeseburger', 'Junetina, cheddar i salata', 10.50, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Burger Corner' AND address = 'Sarajevo, Zmaja od Bosne 8')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Cheeseburger' AND restaurant_id = restaurant.id);

INSERT INTO restaurant (name, address, phone)
SELECT 'Grill Kakanj', 'Kakanj, Centar 5', '061-777-888'
WHERE NOT EXISTS (SELECT 1 FROM restaurant WHERE name = 'Grill Kakanj' AND address = 'Kakanj, Centar 5');

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Cevapi', 'Deset cevapa, somun i luk', 12.00, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Grill Kakanj' AND address = 'Kakanj, Centar 5')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Cevapi' AND restaurant_id = restaurant.id);

INSERT INTO meal (name, description, price, restaurant_id)
SELECT 'Pileci file', 'Piletina sa zara i pomfrit', 11.00, restaurant.id
FROM restaurant
WHERE restaurant.id = (SELECT MIN(id) FROM restaurant WHERE name = 'Grill Kakanj' AND address = 'Kakanj, Centar 5')
AND NOT EXISTS (SELECT 1 FROM meal WHERE name = 'Pileci file' AND restaurant_id = restaurant.id);

SELECT * FROM restaurant;
SELECT * FROM meal;
SELECT food_orders.id, food_orders.customer_name, meal.name AS meal,
       restaurant.name AS restaurant, food_orders.quantity, food_orders.total
FROM food_orders
JOIN meal ON meal.id = food_orders.meal_id
JOIN restaurant ON restaurant.id = meal.restaurant_id;

