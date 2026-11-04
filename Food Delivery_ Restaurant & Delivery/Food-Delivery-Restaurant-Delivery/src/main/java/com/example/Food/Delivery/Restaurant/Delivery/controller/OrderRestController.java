package com.example.Food.Delivery.Restaurant.Delivery.controller;

import com.example.Food.Delivery.Restaurant.Delivery.model.FoodOrder;
import com.example.Food.Delivery.Restaurant.Delivery.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {
    private final OrderService service;
    public OrderRestController(OrderService service) { this.service = service; }
    @GetMapping
    public List<FoodOrder> findAll() { return service.findAll(); }
    @GetMapping("/{id}")
    public FoodOrder findById(@PathVariable Long id) { return service.findById(id); }
}

