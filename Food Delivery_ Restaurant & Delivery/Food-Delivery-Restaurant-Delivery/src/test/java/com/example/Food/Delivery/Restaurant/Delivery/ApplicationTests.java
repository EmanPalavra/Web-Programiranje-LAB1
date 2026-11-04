package com.example.Food.Delivery.Restaurant.Delivery;

import com.example.Food.Delivery.Restaurant.Delivery.model.*;
import com.example.Food.Delivery.Restaurant.Delivery.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ApplicationTests {
    @Autowired private MockMvc mvc;
    @Autowired private RestaurantRepository restaurants;
    @Autowired private MealRepository meals;
    @Autowired private FoodOrderRepository orders;
    private Restaurant restaurant;
    private Meal meal;

    @BeforeEach
    void prepare() {
        restaurant = new Restaurant();
        restaurant.setName("Pizza House");
        restaurant.setAddress("Zenica");
        restaurant.setPhone("061123456");
        restaurants.saveAndFlush(restaurant);
        meal = new Meal();
        meal.setName("Margherita");
        meal.setDescription("Sir");
        meal.setPrice(new BigDecimal("10.00"));
        meal.setRestaurant(restaurant);
        meals.saveAndFlush(meal);
        restaurant.getMeals().add(meal);
    }

    private Long createOrder() throws Exception {
        mvc.perform(post("/orders/add").param("customerName", "Kupac")
                .param("deliveryAddress", "Zenica 12").param("mealId", meal.getId().toString())
                .param("quantity", "2").param("total", "0.01").param("id", "999"))
                .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/orders"));
        orders.flush();
        return orders.findAll().getFirst().getId();
    }

    @Test
    void pagesAndAliasesRender() throws Exception {
        for (String path : new String[]{"/restaurants", "/a", "/meals", "/b", "/orders", "/orders/add"}) {
            mvc.perform(get(path)).andExpect(status().isOk());
        }
        mvc.perform(get("/meals").param("restaurantId", restaurant.getId().toString()))
                .andExpect(content().string(containsString("Margherita")));
        mvc.perform(get("/restaurants/action/" + restaurant.getId()).param("mealId", meal.getId().toString()))
                .andExpect(redirectedUrl("/orders/add?mealId=" + meal.getId()));
        mvc.perform(get("/")).andExpect(redirectedUrl("/restaurants"));
    }

    @Test
    void addPersistsAndCalculatesTotalOnServer() throws Exception {
        createOrder();
        FoodOrder order = orders.findAll().getFirst();
        assertEquals(1, orders.count());
        assertEquals("Kupac", order.getCustomerName());
        assertEquals(meal.getId(), order.getMeal().getId());
        assertEquals(0, new BigDecimal("20.00").compareTo(order.getTotal()));
        mvc.perform(get("/orders")).andExpect(content().string(containsString("20.00 KM")));
    }

    @Test
    void updateChangesExistingOrderAndTotal() throws Exception {
        Long id = createOrder();
        mvc.perform(get("/orders/update/" + id)).andExpect(status().isOk())
                .andExpect(content().string(containsString("Kupac")));
        mvc.perform(post("/orders/update/" + id).param("customerName", "Drugi kupac")
                .param("deliveryAddress", "Nova adresa").param("mealId", meal.getId().toString())
                .param("quantity", "3")).andExpect(redirectedUrl("/orders"));
        orders.flush();
        FoodOrder changed = orders.findById(id).orElseThrow();
        assertEquals(1, orders.count());
        assertEquals("Drugi kupac", changed.getCustomerName());
        assertEquals(3, changed.getQuantity());
        assertEquals(0, new BigDecimal("30.00").compareTo(changed.getTotal()));
    }

    @Test
    void jsonIncludesRelatedMealWithoutRecursion() throws Exception {
        Long id = createOrder();
        mvc.perform(get("/api/orders")).andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].meal.restaurant.name").value("Pizza House"));
        mvc.perform(get("/api/orders/" + id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.total").value(20.0));
    }

    @Test
    void invalidFormDoesNotCreateOrder() throws Exception {
        for (String quantity : new String[]{"", "0", "-1", "21", "1.5", "abc"}) {
            mvc.perform(post("/orders/add").param("customerName", "Kupac")
                    .param("deliveryAddress", "Adresa").param("mealId", meal.getId().toString())
                    .param("quantity", quantity)).andExpect(status().isOk())
                    .andExpect(model().attributeHasFieldErrors("orderForm", "quantity"));
        }
        mvc.perform(post("/orders/add").param("customerName", " ")
                .param("deliveryAddress", "").param("quantity", "1"))
                .andExpect(model().attributeHasFieldErrors("orderForm", "customerName", "deliveryAddress", "mealId"));
        assertEquals(0, orders.count());
    }

    @Test
    void invalidUpdatePreservesSavedValues() throws Exception {
        Long id = createOrder();
        mvc.perform(post("/orders/update/" + id).param("customerName", "Kupac")
                .param("deliveryAddress", "Adresa").param("mealId", meal.getId().toString())
                .param("quantity", "0")).andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("orderForm", "quantity"))
                .andExpect(model().attribute("orderId", id));
        assertEquals(2, orders.findById(id).orElseThrow().getQuantity());
    }

    @Test
    void missingRecordsReturn404AndDoNotSave() throws Exception {
        for (String path : new String[]{"/api/orders/999999", "/orders/update/999999", "/meals?restaurantId=999999"}) {
            mvc.perform(get(path)).andExpect(status().isNotFound());
        }
        mvc.perform(post("/orders/add").param("customerName", "Kupac").param("deliveryAddress", "Adresa")
                .param("mealId", "999999").param("quantity", "1")).andExpect(status().isNotFound());
        mvc.perform(post("/orders/update/999999").param("customerName", "Kupac").param("deliveryAddress", "Adresa")
                .param("mealId", meal.getId().toString()).param("quantity", "1")).andExpect(status().isNotFound());
        assertEquals(0, orders.count());
    }

    @Test
    void mealFromAnotherRestaurantIsRejected() throws Exception {
        Restaurant other = new Restaurant();
        other.setName("Drugi restoran");
        restaurants.saveAndFlush(other);
        mvc.perform(get("/restaurants/action/" + other.getId()).param("mealId", meal.getId().toString()))
                .andExpect(status().isNotFound());
    }
}

