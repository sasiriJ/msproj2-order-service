package com.sasiri.productapp.orderservice.service;

import com.sasiri.productapp.orderservice.client.InventoryClient;
import com.sasiri.productapp.orderservice.dto.OrderRequest;
import com.sasiri.productapp.orderservice.event.OrderPlacedEvent;
import com.sasiri.productapp.orderservice.model.Order;
import com.sasiri.productapp.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

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

            //Send message to kafka topic
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email() );
            log.info("Start- sending orderPlacedEvent {} to kafka topic.",orderPlacedEvent);
            kafkaTemplate.send("order-placed", orderPlacedEvent);
            log.info("End- successfully sent orderPlacedEvent {} to kafka topic.",orderPlacedEvent);


            return order;
        }else {
            throw new RuntimeException("Product with SkuCode "+ orderRequest.skuCode() + " is not in stock");
        }

    }
}
