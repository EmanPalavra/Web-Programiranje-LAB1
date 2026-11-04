package com.example.Food.Delivery.Restaurant.Delivery.service;

import com.example.Food.Delivery.Restaurant.Delivery.model.*;
import com.example.Food.Delivery.Restaurant.Delivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderService {
    private final FoodOrderRepository repo;
    private final MealRepository meals;
    public OrderService(FoodOrderRepository repo, MealRepository meals) {
        this.repo = repo;
        this.meals = meals;
    }
    public List<FoodOrder> findAll() { return repo.findAll(); }
    public FoodOrder findById(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Narudzba nije pronadjena."));
    }
    public FoodOrder save(Long id, OrderForm form) {
        FoodOrder order = id == null ? new FoodOrder() : findById(id);
        Meal meal = meals.findById(form.getMealId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Jelo nije pronadjeno."));
        order.setCustomerName(form.getCustomerName().strip());
        order.setDeliveryAddress(form.getDeliveryAddress().strip());
        order.setQuantity(form.getQuantity());
        order.setMeal(meal);
        order.setTotal(meal.getPrice().multiply(BigDecimal.valueOf(form.getQuantity())));
        return repo.save(order);
    }
}

