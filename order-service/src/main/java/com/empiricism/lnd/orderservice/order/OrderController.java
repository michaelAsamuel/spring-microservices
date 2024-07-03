package com.empiricism.lnd.orderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        try{
            orderService.createOrder(orderRequest);
            return "order placed successfully";
        }catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllProducts(){
        return orderService.getAllOrders();
    }

}
