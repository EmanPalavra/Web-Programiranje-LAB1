package com.example.Food.Delivery.Restaurant.Delivery;

import com.example.Food.Delivery.Restaurant.Delivery.data.DemoData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DemoData data;

    @Test
    void pagesRender() throws Exception {
        for (String path : new String[]{"/restaurants", "/a", "/meals", "/b",
                "/meals?restaurantId=1", "/restaurants/action/1?mealId=1", "/a/action/1?mealId=1"}) {
            mvc.perform(get(path)).andExpect(status().isOk());
        }
        mvc.perform(get("/")).andExpect(redirectedUrl("/restaurants"));
        mvc.perform(get("/restaurants/action/1")).andExpect(redirectedUrl("/meals?restaurantId=1"));
    }

    @Test
    void menuShowsOnlySelectedRestaurant() throws Exception {
        mvc.perform(get("/meals").param("restaurantId", "1"))
                .andExpect(model().attribute("meals", data.findRestaurant(1L).getMeals()))
                .andExpect(content().string(containsString("Margherita")));
    }

    @Test
    void orderShowsConfirmationAndCorrectTotal() throws Exception {
        int mealCount = data.findAllMeals().size();
        var result = mvc.perform(post("/restaurants/action/1")
                        .param("mealId", "1").param("quantity", "2"))
                .andExpect(redirectedUrl("/restaurants/action/1?mealId=1"))
                .andExpect(flash().attribute("ordered", true))
                .andExpect(flash().attribute("quantity", 2))
                .andExpect(flash().attribute("total", new java.math.BigDecimal("20.00")))
                .andReturn();

        mvc.perform(get("/restaurants/action/1?mealId=1")
                        .flashAttrs(result.getFlashMap()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Narudzba potvrdjena")))
                .andExpect(content().string(containsString("20.00 KM")));

        assertEquals(mealCount, data.findAllMeals().size());
    }

    @Test
    void invalidQuantityShowsError() throws Exception {
        for (String quantity : new String[]{"", "0", "-1", "21", "1.5", "abc"}) {
            mvc.perform(post("/restaurants/action/1")
                            .param("mealId", "1").param("quantity", quantity))
                    .andExpect(status().isOk())
                    .andExpect(view().name("action"))
                    .andExpect(model().attributeExists("error"))
                    .andExpect(model().attributeDoesNotExist("ordered"));
        }
    }

    @Test
    void missingOrMismatchedDataIsRejected() throws Exception {
        mvc.perform(get("/meals?restaurantId=999")).andExpect(status().isNotFound());
        mvc.perform(get("/restaurants/action/999?mealId=1")).andExpect(status().isNotFound());
        mvc.perform(get("/restaurants/action/1?mealId=999")).andExpect(status().isNotFound());
        mvc.perform(post("/restaurants/action/1").param("mealId", "3").param("quantity", "1"))
                .andExpect(status().isNotFound());
        mvc.perform(post("/restaurants/action/1").param("quantity", "1"))
                .andExpect(status().isBadRequest());
    }
}

