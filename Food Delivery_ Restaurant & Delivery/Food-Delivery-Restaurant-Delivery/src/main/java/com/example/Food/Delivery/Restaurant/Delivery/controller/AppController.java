package com.example.Food.Delivery.Restaurant.Delivery.controller;

import com.example.Food.Delivery.Restaurant.Delivery.data.DemoData;
import com.example.Food.Delivery.Restaurant.Delivery.model.Meal;
import com.example.Food.Delivery.Restaurant.Delivery.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;

@Controller
public class AppController {

    private final DemoData data;

    public AppController(DemoData data) {
        this.data = data;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/restaurants";
    }

    @GetMapping({"/restaurants", "/a"})
    public String restaurants(Model model) {
        model.addAttribute("restaurants", data.findAllRestaurants());
        return "restaurants";
    }

    @GetMapping({"/meals", "/b"})
    public String meals(@RequestParam(required = false) Long restaurantId, Model model) {
        if (restaurantId == null) {
            model.addAttribute("meals", data.findAllMeals());
        } else {
            Restaurant restaurant = requireRestaurant(restaurantId);
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("meals", restaurant.getMeals());
        }
        return "meals";
    }

    @GetMapping({"/restaurants/action/{id}", "/a/action/{id}"})
    public String action(@PathVariable Long id,
                         @RequestParam(required = false) Long mealId, Model model) {
        Restaurant restaurant = requireRestaurant(id);
        if (mealId == null) {
            return "redirect:/meals?restaurantId=" + id;
        }

        Meal meal = requireMeal(mealId, restaurant);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("meal", meal);
        return "action";
    }

    @PostMapping({"/restaurants/action/{id}", "/a/action/{id}"})
    public String order(@PathVariable Long id,
                        @RequestParam Long mealId,
                        @RequestParam(defaultValue = "") String quantity,
                        Model model, RedirectAttributes redirect) {
        Restaurant restaurant = requireRestaurant(id);
        Meal meal = requireMeal(mealId, restaurant);
        int amount = 0;

        try {
            amount = Integer.parseInt(quantity);
        } catch (NumberFormatException ex) {
            amount = 0;
        }

        if (amount < 1 || amount > 20) {
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("meal", meal);
            model.addAttribute("error", "Kolicina mora biti cijeli broj od 1 do 20.");
            return "action";
        }

        BigDecimal total = meal.getPrice().multiply(BigDecimal.valueOf(amount));
        redirect.addFlashAttribute("ordered", true);
        redirect.addFlashAttribute("quantity", amount);
        redirect.addFlashAttribute("total", total);
        return "redirect:/restaurants/action/" + id + "?mealId=" + mealId;
    }

    private Restaurant requireRestaurant(Long id) {
        Restaurant restaurant = data.findRestaurant(id);
        if (restaurant == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restoran nije pronadjen.");
        }
        return restaurant;
    }

    private Meal requireMeal(Long id, Restaurant restaurant) {
        Meal meal = data.findMeal(id);
        if (meal == null || meal.getRestaurant() != restaurant) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Jelo nije pronadjeno u ovom restoranu.");
        }
        return meal;
    }
}

