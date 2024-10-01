package com.eBookStore.OnlineBookStoreProject.controller;

import com.eBookStore.OnlineBookStoreProject.dto.AddBookInCartDto;
import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.dto.PlaceOrderDto;
import com.eBookStore.OnlineBookStoreProject.exceptions.ValidationException;
import com.eBookStore.OnlineBookStoreProject.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> addBookToCart(@RequestBody AddBookInCartDto addBookInCartDto) throws JsonProcessingException {
       // return cartService.addBookToCart(addBookInCartDto);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cartService.addBookToCart(addBookInCartDto));
        return ResponseEntity.ok(json);
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable Long userId){
        OrderDto orderDto=cartService.getCartByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }


    @GetMapping("/coupon/{userId}/{code}")
    public ResponseEntity<?> applyCoupon(@PathVariable Long userId, @PathVariable String code){
        try{
            OrderDto orderDto=cartService.applyCoupon(userId,code);
            return ResponseEntity.ok(orderDto);
        }catch (ValidationException ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getLocalizedMessage());

        }
    }

    @PostMapping("/addition")
    public ResponseEntity<?> increaseBookQuantity(@RequestBody AddBookInCartDto addBookInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.increaseBookQuantity(addBookInCartDto));
    }

    @PostMapping("/deduction")
    public ResponseEntity<?> decreaseBookQuantity(@RequestBody AddBookInCartDto addBookInCartDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.decreaseBookQuantity(addBookInCartDto));
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<OrderDto> placeOrder(@RequestBody PlaceOrderDto placeOrderDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.placeOrder(placeOrderDto));
    }

    @GetMapping("/myOrders/{userId}")
    public ResponseEntity<List<OrderDto>> getPlacedOrders(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.getMyPlacedOrders(userId));
    }
}
