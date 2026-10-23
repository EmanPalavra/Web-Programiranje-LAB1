package com.example.Food.Delivery.Restaurant.Delivery.model;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private final Long id;
    private final String name;
    private final String address;
    private final String phone;
    private final List<Meal> meals = new ArrayList<>();

    public Restaurant(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    public void addMeal(Meal meal) {
        if (meal.getRestaurant() != this) {
            throw new IllegalArgumentException("Jelo ne pripada ovom restoranu.");
        }
        meals.add(meal);
    }
}
