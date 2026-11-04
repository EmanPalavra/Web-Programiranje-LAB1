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


