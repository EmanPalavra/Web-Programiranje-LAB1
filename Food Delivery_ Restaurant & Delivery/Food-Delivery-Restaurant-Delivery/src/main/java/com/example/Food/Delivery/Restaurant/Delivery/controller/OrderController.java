package com.example.Food.Delivery.Restaurant.Delivery.controller;

import com.example.Food.Delivery.Restaurant.Delivery.model.*;
import com.example.Food.Delivery.Restaurant.Delivery.repository.MealRepository;
import com.example.Food.Delivery.Restaurant.Delivery.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;
    private final MealRepository meals;
    public OrderController(OrderService service, MealRepository meals) {
        this.service = service;
        this.meals = meals;
    }
    @GetMapping
    public String showAll(Model model) {
        model.addAttribute("orders", service.findAll());
        return "orders";
    }
    @GetMapping("/add")
    public String add(@RequestParam(required = false) Long mealId, Model model) {
        OrderForm form = new OrderForm();
        form.setMealId(mealId);
        model.addAttribute("orderForm", form);
        model.addAttribute("meals", meals.findAll());
        return "order-form";
    }
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("orderForm") OrderForm form,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("meals", meals.findAll());
            return "order-form";
        }
        service.save(null, form);
        return "redirect:/orders";
    }
    @GetMapping("/update/{id}")
    public String edit(@PathVariable Long id, Model model) {
        FoodOrder order = service.findById(id);
        OrderForm form = new OrderForm();
        form.setCustomerName(order.getCustomerName());
        form.setDeliveryAddress(order.getDeliveryAddress());
        form.setMealId(order.getMeal().getId());
        form.setQuantity(order.getQuantity());
        model.addAttribute("orderForm", form);
        model.addAttribute("orderId", id);
        model.addAttribute("meals", meals.findAll());
        return "order-form";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("orderForm") OrderForm form,
                         BindingResult result, Model model) {
        service.findById(id);
        if (result.hasErrors()) {
            model.addAttribute("orderId", id);
            model.addAttribute("meals", meals.findAll());
            return "order-form";
        }
        service.save(id, form);
        return "redirect:/orders";
    }
}

