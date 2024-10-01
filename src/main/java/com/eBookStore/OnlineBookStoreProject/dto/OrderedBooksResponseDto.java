package com.eBookStore.OnlineBookStoreProject.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderedBooksResponseDto {

    private List<BooksDto> booksDtos;

    private Long orderAmount;
}
