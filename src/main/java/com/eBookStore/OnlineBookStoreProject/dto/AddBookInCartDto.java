package com.eBookStore.OnlineBookStoreProject.dto;


import lombok.Data;

@Data
public class AddBookInCartDto {
    private Long userId;
    private Long bookId;
}
