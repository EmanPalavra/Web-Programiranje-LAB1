package com.example.Food.Delivery.Restaurant.Delivery.model;

import java.math.BigDecimal;

public class Meal {

    private final Long id;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final Restaurant restaurant;

    public Meal(Long id, String name, String description, BigDecimal price, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
