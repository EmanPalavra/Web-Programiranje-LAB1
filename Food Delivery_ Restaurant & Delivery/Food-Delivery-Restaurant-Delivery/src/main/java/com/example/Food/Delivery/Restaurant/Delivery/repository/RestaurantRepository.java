package com.example.Food.Delivery.Restaurant.Delivery.repository;

import com.example.Food.Delivery.Restaurant.Delivery.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}

