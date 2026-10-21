package com.example.Food.Delivery.Restaurant.Delivery.data;

import com.example.Food.Delivery.Restaurant.Delivery.model.Restaurant;
import com.example.Food.Delivery.Restaurant.Delivery.model.Delivery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DemoData {

    public List<Restaurant> restaurants = new ArrayList<>();
    public List<Delivery> deliveries = new ArrayList<>();

    public DemoData() {

        Restaurant r1 = new Restaurant(1L, "Pizza House", "Zenica", "061-123-456");
        Restaurant r2 = new Restaurant(2L, "Burger King", "Sarajevo", "062-555-444");
        Restaurant r3 = new Restaurant(3L, "Fast Food", "Kakanj", "061-777-888");

        restaurants.add(r1);
        restaurants.add(r2);
        restaurants.add(r3);

        addDelivery("Amar", "Zenica", r1);
        addDelivery("Emina", "Kakanj", r1);
        addDelivery("Haris", "Sarajevo", r2);
    }

    public void addDelivery(String customer, String address, Restaurant restaurant) {

        Delivery delivery = new Delivery(
                (long) deliveries.size() + 1,
                customer,
                address,
                "Na cekanju",
                restaurant
        );

        deliveries.add(delivery);
        restaurant.getDeliveries().add(delivery);
    }

    public Restaurant findRestaurant(Long id) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getId().equals(id)) {
                return restaurant;
            }
        }

        return null;
    }
}