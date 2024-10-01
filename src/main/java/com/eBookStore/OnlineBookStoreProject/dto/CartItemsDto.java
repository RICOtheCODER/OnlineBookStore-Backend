package com.eBookStore.OnlineBookStoreProject.dto;

import lombok.Data;

@Data
public class CartItemsDto {
    private Long id;
    private Long price;
    private Long quantity;
    private Long bookId;
    private Long orderId;
    private String bookName;
    private byte[] returnedImg;
    private Long userId;
}
