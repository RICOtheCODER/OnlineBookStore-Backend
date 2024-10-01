package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    public List<OrderDto> getAllPlacedOrders();

    public OrderDto changeOrderStatus(Long orderId,String status);
}
