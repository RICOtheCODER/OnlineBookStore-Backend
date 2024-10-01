package com.eBookStore.OnlineBookStoreProject.repository;

import com.eBookStore.OnlineBookStoreProject.enums.OrderStatus;
import com.eBookStore.OnlineBookStoreProject.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus status);
    Order findByUserId(Long userId);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    List<Order> findByUserIdAndOrderStatusIn(Long userId,List<OrderStatus> orderStatus);
}
