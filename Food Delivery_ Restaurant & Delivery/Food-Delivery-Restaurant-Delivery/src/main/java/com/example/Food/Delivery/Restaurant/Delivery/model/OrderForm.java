package com.example.Food.Delivery.Restaurant.Delivery.model;

import jakarta.validation.constraints.*;

public class OrderForm {
    @NotBlank(message = "Unesite ime kupca.")
    @Size(max = 100)
    private String customerName;
    @NotBlank(message = "Unesite adresu dostave.")
    @Size(max = 255)
    private String deliveryAddress;
    @NotNull(message = "Izaberite jelo.")
    private Long mealId;
    @NotNull(message = "Unesite kolicinu.")
    @Min(value = 1, message = "Najmanja kolicina je 1.")
    @Max(value = 20, message = "Najveca kolicina je 20.")
    private Integer quantity = 1;
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    public Long getMealId() { return mealId; }
    public void setMealId(Long mealId) { this.mealId = mealId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}

