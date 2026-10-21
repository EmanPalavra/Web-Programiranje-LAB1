package com.example.Food.Delivery.Restaurant.Delivery.model;

import java.util.ArrayList;
import  java.util.List;

public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private List<Delivery> deliveries  = new ArrayList<>();

    public Restaurant(Long id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone(){
        return phone;
    }

    public List<Delivery>getDeliveries(){
        return deliveries;
    }
}
