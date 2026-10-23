package com.example.Food.Delivery.Restaurant.Delivery.data;

import com.example.Food.Delivery.Restaurant.Delivery.model.Meal;
import com.example.Food.Delivery.Restaurant.Delivery.model.Restaurant;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DemoData {

    private final List<Restaurant> restaurants = new ArrayList<>();
    private final List<Meal> meals = new ArrayList<>();
    private long nextId = 1;

    public DemoData() {
        Restaurant r1 = new Restaurant(1L, "Pizza House", "Zenica, Titova 12", "061-123-456");
        Restaurant r2 = new Restaurant(2L, "Burger Corner", "Sarajevo, Zmaja od Bosne 8", "062-555-444");
        Restaurant r3 = new Restaurant(3L, "Grill Kakanj", "Kakanj, Centar 5", "061-777-888");

        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);

        addMeal("Margherita", "Paradajz, mozzarella i bosiljak", new BigDecimal("10.00"), r1);
        addMeal("Capricciosa", "Sir, sunka i gljive", new BigDecimal("13.50"), r1);
        addMeal("Classic burger", "Junetina, salata i domaci sos", new BigDecimal("9.00"), r2);
        addMeal("Cheeseburger", "Junetina, cheddar i salata", new BigDecimal("10.50"), r2);
        addMeal("Cevapi", "Deset cevapa, somun i luk", new BigDecimal("12.00"), r3);
        addMeal("Pileci file", "Piletina sa zara i pomfrit", new BigDecimal("11.00"), r3);
    }

    public List<Restaurant> findAllRestaurants() {
        return new ArrayList<>(restaurants);
    }

    public synchronized List<Meal> findAllMeals() {
        return new ArrayList<>(meals);
    }

    public Restaurant findRestaurant(Long id) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId().equals(id)) {
                return restaurant;
            }
        }
        return null;
    }

    public Meal findMeal(Long id) {
        for (Meal meal : meals) {
            if (meal.getId().equals(id)) {
                return meal;
            }
        }
        return null;
    }

    public synchronized Meal addMeal(String name, String description, BigDecimal price,
                                     Restaurant restaurant) {
        if (name == null || name.isBlank() || name.strip().length() > 100) {
            throw new IllegalArgumentException("Neispravan naziv jela.");
        }

        if (description == null || description.isBlank() || description.strip().length() > 500) {
            throw new IllegalArgumentException("Neispravan opis jela.");
        }

        if (price == null || price.signum() <= 0
                || price.compareTo(new BigDecimal("9999.99")) > 0
                || price.stripTrailingZeros().scale() > 2) {
            throw new IllegalArgumentException("Neispravna cijena jela.");
        }

        if (!restaurants.contains(restaurant)) {
            throw new IllegalArgumentException("Restoran nije pronadjen.");
        }

        Meal meal = new Meal(nextId++, name.strip(), description.strip(),
                price.setScale(2), restaurant);

        meals.add(meal);
        restaurant.addMeal(meal);
        return meal;
    }
}
