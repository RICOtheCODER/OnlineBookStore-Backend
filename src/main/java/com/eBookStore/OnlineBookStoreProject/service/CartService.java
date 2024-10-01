package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.AddBookInCartDto;
import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartService {
    public ResponseEntity<?> addBookToCart(AddBookInCartDto addBookInCartDto);

    public OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId,String code);

    OrderDto increaseBookQuantity(AddBookInCartDto addBookInCartDto);

    OrderDto decreaseBookQuantity(AddBookInCartDto addBookInCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    public List<OrderDto> getMyPlacedOrders(Long userId);
}
