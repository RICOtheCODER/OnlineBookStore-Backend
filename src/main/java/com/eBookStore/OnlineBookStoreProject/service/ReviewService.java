package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.OrderedBooksResponseDto;

public interface ReviewService {

    OrderedBooksResponseDto getOrderedBookDetailsByOrderId(Long orderId);
}
