package com.sasiri.productapp.orderservice.controller;

import com.sasiri.productapp.orderservice.dto.OrderRequest;
import com.sasiri.productapp.orderservice.model.Order;
import com.sasiri.productapp.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.placeOrder(orderRequest), HttpStatusCode.valueOf(201));
    }
}
