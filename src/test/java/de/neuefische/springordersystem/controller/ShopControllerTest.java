package de.neuefische.springordersystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.springordersystem.model.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;



import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
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
    void whenAddOrder_returnOrderWithId_andStatusCode200_version() throws Exception {
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

    @Test
    @DirtiesContext
    void whenGetOrderById_thenReturn200OK_returnCorrectOrder() throws Exception {
       MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post("/api/orders/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1]"))
                .andExpect(status().isOk())
               .andReturn();

       String content = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(content, Order.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/"+order.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "products": [
                                {
                                    "id": 1,
                                    "name": "Apfel"
                                }
                            ]
                        }
                        """)).andExpect(jsonPath("$.id").value(order.getId()));

    }

    @Test
    @DirtiesContext
    void whenGetProductById_value1_returnCorrectProduct_andStatusCode200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {
                            "id": 1,
                            "name": "Apfel"
                        }
                        """));
    }
    @Test
    @DirtiesContext
    void whenGetProductById_valueInvalid_throwsNoSuchElementException() {
        try{mockMvc.perform(MockMvcRequestBuilders.get("/api/products/100"));
        fail();}
        catch (Exception e){
            assertTrue(true);
        }
    }
}