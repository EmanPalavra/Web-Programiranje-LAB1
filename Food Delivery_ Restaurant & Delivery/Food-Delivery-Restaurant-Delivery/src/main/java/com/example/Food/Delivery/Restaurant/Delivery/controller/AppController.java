package com.example.Food.Delivery.Restaurant.Delivery.controller;

import com.example.Food.Delivery.Restaurant.Delivery.model.*;
import com.example.Food.Delivery.Restaurant.Delivery.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AppController {
    private final RestaurantRepository restaurants;
    private final MealRepository meals;
    public AppController(RestaurantRepository restaurants, MealRepository meals) {
        this.restaurants = restaurants;
        this.meals = meals;
    }
    @GetMapping("/")
    public String home() { return "redirect:/restaurants"; }
    @GetMapping({"/restaurants", "/a"})
    public String restaurants(Model model) {
        model.addAttribute("restaurants", restaurants.findAll());
        return "restaurants";
    }
    @GetMapping({"/meals", "/b"})
    public String meals(@RequestParam(required = false) Long restaurantId, Model model) {
        if (restaurantId == null) {
            model.addAttribute("meals", meals.findAll());
        } else {
            Restaurant restaurant = restaurants.findById(restaurantId).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Restoran nije pronadjen."));
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("meals", restaurant.getMeals());
        }
        return "meals";
    }
    @GetMapping({"/restaurants/action/{id}", "/a/action/{id}"})
    public String action(@PathVariable Long id, @RequestParam(required = false) Long mealId) {
        if (!restaurants.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restoran nije pronadjen.");
        }
        if (mealId == null) { return "redirect:/meals?restaurantId=" + id; }
        Meal meal = meals.findById(mealId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Jelo nije pronadjeno."));
        if (!meal.getRestaurant().getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jelo ne pripada restoranu.");
        }
        return "redirect:/orders/add?mealId=" + mealId;
    }
}

