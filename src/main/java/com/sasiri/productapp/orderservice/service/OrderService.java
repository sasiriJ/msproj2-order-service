package com.sasiri.productapp.orderservice.service;

import com.sasiri.productapp.orderservice.client.InventoryClient;
import com.sasiri.productapp.orderservice.dto.OrderRequest;
import com.sasiri.productapp.orderservice.model.Order;
import com.sasiri.productapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public Order placeOrder(OrderRequest orderRequest) {
        boolean isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        if(isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());
            System.out.print(order);
            orderRepository.save(order);
            return order;
        }else {
            throw new RuntimeException("Product with SkuCode "+ orderRequest.skuCode() + " is not in stock");
        }


    }
}
