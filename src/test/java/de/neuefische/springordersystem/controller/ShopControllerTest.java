package de.neuefische.springordersystem.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class ShopControllerTest {

    @Autowired
    MockMvc mockMvc;


    @Test
    @DirtiesContext
    void whenGetOrders_returnEmptyOrderList_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
    @Test
    @DirtiesContext
    void whenAddOrder_returnOrderWithIc_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/add")
                        .contentType("application/json")
                        .content("[1]"))
                .andExpect(status().isOk());
//                .andExpect(content().json("""
//                        {
//                            "products": [
//                                {
//                                    "id": 1,
//                                    "name": "Apfel"
//                                }
//                            ]
//                        }
//                        """)).andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @DirtiesContext
    void whenAddOrder_returnOrderWithId_andStatusCode200_version2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/add")
                        .contentType("application/json")
                        .content("[1]"))
                .andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                            {
                                "products": [
                                    {
                                        "id": 1,
                                        "name": "Apfel"
                                    }
                                ]
                            }
                        ]
                        """)).andExpect(jsonPath("$[0].id").isNotEmpty());
    }

}