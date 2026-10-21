package com.example.Food.Delivery.Restaurant.Delivery.model;

public class Delivery {

    private Long id;
    private String customer;
    private String address;
    private String status;
    private Restaurant restaurant;

    public Delivery(Long id , String customer, String adress, String status, Restaurant restaurant){
        this.id = id;
        this.customer = customer;
        this.address = adress;
        this.status = status;
        this.restaurant = restaurant;
    }

    public Long getId(){
        return  id;
    }

    public String getCustomer(){
        return customer;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus(){
        return status;

    }
    public Restaurant getRestaurant(){
        return restaurant;
    }
}
