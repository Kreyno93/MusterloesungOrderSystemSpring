package de.neuefische.springordersystem.service;

import de.neuefische.springordersystem.model.Order;
import de.neuefische.springordersystem.model.Product;
import de.neuefische.springordersystem.repo.OrderRepo;
import de.neuefische.springordersystem.repo.ProductRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ShopServiceTest {

    OrderRepo orderRepo = mock(OrderRepo.class);
    ProductRepo productRepo = mock(ProductRepo.class);
    GenerateUUID generateUUID = mock(GenerateUUID.class);

    ShopService shopService = new ShopService(productRepo,orderRepo,generateUUID);

    @Test
    void whenAddOrder_addCorrectProducts() {
        //GIVEN
        ArrayList<Integer> productIds = new ArrayList<>();
        productIds.add(1);
        productIds.add(2);

        when(productRepo.getProduct(1)).thenReturn(new Product(1, "Apfel"));
        when(productRepo.getProduct(2)).thenReturn(new Product(2, "Birne"));
        when(generateUUID.generateUUID()).thenReturn("1");
        List<Product> products = new ArrayList<>(List.of(
                productRepo.getProduct(1),
                productRepo.getProduct(2)
        ));

        //WHEN
        shopService.addOrder(productIds);

        //THEN
        verify(orderRepo).addOrder(new Order("1", products));
    }




}