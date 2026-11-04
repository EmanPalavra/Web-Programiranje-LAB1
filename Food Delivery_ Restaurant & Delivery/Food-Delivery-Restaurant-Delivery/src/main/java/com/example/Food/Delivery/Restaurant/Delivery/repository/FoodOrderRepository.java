package com.example.Food.Delivery.Restaurant.Delivery.repository;

import com.example.Food.Delivery.Restaurant.Delivery.model.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
}

