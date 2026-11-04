package com.example.Food.Delivery.Restaurant.Delivery.repository;

import com.example.Food.Delivery.Restaurant.Delivery.model.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}

