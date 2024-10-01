package com.eBookStore.OnlineBookStoreProject.controller;


import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminOrderController {
    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/placedOrders")
    public ResponseEntity<List<OrderDto>> getAllPlacedOrders(){
        return ResponseEntity.ok(adminOrderService.getAllPlacedOrders());
    }

    @GetMapping("/order/{orderId}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long orderId,@PathVariable String status){
        OrderDto orderDto=adminOrderService.changeOrderStatus(orderId,status);
        if(orderDto==null){
            return new ResponseEntity<>("Something Went Wrong", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderDto);
    }
}