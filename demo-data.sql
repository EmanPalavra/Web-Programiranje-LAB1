USE food_delivery_db;

INSERT INTO restaurant (name, address, phone) VALUES ('Pizza House', 'Zenica, Titova 12', '061-123-456');
SET @pizza = LAST_INSERT_ID();
INSERT INTO meal (name, description, price, restaurant_id) VALUES
('Margherita', 'Paradajz, mozzarella i bosiljak', 10.00, @pizza),
('Capricciosa', 'Sir, sunka i gljive', 13.50, @pizza);

INSERT INTO restaurant (name, address, phone) VALUES ('Burger Corner', 'Sarajevo, Zmaja od Bosne 8', '062-555-444');
SET @burger = LAST_INSERT_ID();
INSERT INTO meal (name, description, price, restaurant_id) VALUES
('Classic burger', 'Junetina, salata i domaci sos', 9.00, @burger),
('Cheeseburger', 'Junetina, cheddar i salata', 10.50, @burger);

INSERT INTO restaurant (name, address, phone) VALUES ('Grill Kakanj', 'Kakanj, Centar 5', '061-777-888');
SET @grill = LAST_INSERT_ID();
INSERT INTO meal (name, description, price, restaurant_id) VALUES
('Cevapi', 'Deset cevapa, somun i luk', 12.00, @grill),
('Pileci file', 'Piletina sa zara i pomfrit', 11.00, @grill);

