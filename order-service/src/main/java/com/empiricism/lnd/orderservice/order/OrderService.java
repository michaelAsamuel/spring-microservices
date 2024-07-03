package com.empiricism.lnd.orderservice.order;

import com.empiricism.lnd.orderservice.config.WebClientConfig;
import com.empiricism.lnd.orderservice.inventory.InventoryResponseDto;
import io.netty.resolver.DefaultAddressResolverGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ClientTransport;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public void createOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineitem> orderLineitemList= orderRequest.getOrderLineitemDtoList().stream()
                .map(this::mapDtoToOrderLineitem)
                .toList();

        order.setOrderLineitemList(orderLineitemList);

        // Don't make calls for each lineitem consolidate to single call
        List<String> skuCodes = order.getOrderLineitemList().stream()
                .map(OrderLineitem::getSkuCode)
                .toList();

        // Call Inventory Service to check if stock is available
        var inventoryResponseDtoArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono( InventoryResponseDto[].class)
                .block();

        // System.out.println(inventoryResponseDtoArray[0].toString());
        boolean hasAllProductsInStock =  Arrays.stream(inventoryResponseDtoArray)
                .allMatch(InventoryResponseDto::isInStock);

        if(hasAllProductsInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
    }

    private OrderLineitem mapDtoToOrderLineitem(OrderLineitemDto orderLineitemDto) {
        OrderLineitem orderLineitem = new OrderLineitem();
        orderLineitem.setPrice(orderLineitemDto.getPrice());
        orderLineitem.setQuantity(orderLineitemDto.getQuantity());
        orderLineitem.setSkuCode(orderLineitemDto.getSkuCode());
        return orderLineitem;

    }

    public List<OrderResponse> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderLineitemList(order.getOrderLineitemList())
                .orderNumber(order.getOrderNumber())
                .build();
    }

}
