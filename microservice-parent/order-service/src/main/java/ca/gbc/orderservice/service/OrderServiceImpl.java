package ca.gbc.orderservice.service;

import ca.gbc.orderservice.client.InventoryClient;
import ca.gbc.orderservice.dto.OrderRequest;
import ca.gbc.orderservice.model.Order;
import ca.gbc.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {

        // The better solution is to use queue to store the orders which weren't placed due to product being unavailable
        // and client won't be charged at that moment but once the inventory is reestablished the order will be processed
        // and client will be charged for that.

        //check inventory before processing the order
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());

        // If isProductInStock returns true process the order or else do not.
        if (isProductInStock) {
            Order order = Order.builder()
                    .orderNumber(UUID.randomUUID().toString())
                    .price(orderRequest.price())
                    .skuCode(orderRequest.skuCode())
                    .quantity(orderRequest.quantity())
                    .build();

            orderRepository.save(order);
        }else {
            throw new RuntimeException("Product with skuCode " + orderRequest.skuCode() + " is not in stock");
        }

    }
}
