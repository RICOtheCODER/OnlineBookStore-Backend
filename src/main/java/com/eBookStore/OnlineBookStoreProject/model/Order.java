package com.eBookStore.OnlineBookStoreProject.model;


import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;



    private String orderDescription;

    private Date date;

    private Long amount;

    private String address;

    private String payment;

    private OrderStatus orderStatus;

    private Long totalAmount;

    private Long discount;

    private UUID trackingId;
    @ManyToOne(cascade = CascadeType.MERGE)

    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private List<CartItems> cartItems;

    public OrderDto getOrderDto(){
        OrderDto orderDto=new OrderDto();
        orderDto.setId(id);
        System.out.println("Id" +id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());
        if(coupon!=null){
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;
    }
}
