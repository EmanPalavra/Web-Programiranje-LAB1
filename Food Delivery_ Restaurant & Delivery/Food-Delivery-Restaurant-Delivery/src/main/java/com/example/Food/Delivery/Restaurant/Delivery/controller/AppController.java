package com.example.Food.Delivery.Restaurant.Delivery.controller;

import com.example.Food.Delivery.Restaurant.Delivery.data.DemoData;
import com.example.Food.Delivery.Restaurant.Delivery.model.Restaurant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        model.addAttribute("restaurants", data.restaurants);
        return "restaurants";
    }

    @GetMapping("/deliveries")
    public String deliveries(Model model) {
        model.addAttribute("deliveries", data.deliveries);
        return "deliveries";
    }

    @GetMapping("/restaurants/action/{id}")
    public String action(@PathVariable Long id, Model model) {

        Restaurant restaurant = data.findRestaurant(id);

        if (restaurant == null) {
            return "redirect:/restaurants";
        }

        model.addAttribute("restaurant", restaurant);
        return "action";
    }

    @PostMapping("/restaurants/action/{id}")
    public String addDelivery(@PathVariable Long id,
                              @RequestParam String customer,
                              @RequestParam String address) {

        Restaurant restaurant = data.findRestaurant(id);

        if (restaurant != null) {
            data.addDelivery(customer, address, restaurant);
        }

        return "redirect:/deliveries";
    }
}
