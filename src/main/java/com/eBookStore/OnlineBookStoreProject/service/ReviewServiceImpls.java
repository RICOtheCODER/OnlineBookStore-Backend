package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.dto.OrderedBooksResponseDto;
import com.eBookStore.OnlineBookStoreProject.model.Books;
import com.eBookStore.OnlineBookStoreProject.model.CartItems;
import com.eBookStore.OnlineBookStoreProject.model.Order;
import com.eBookStore.OnlineBookStoreProject.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpls  implements ReviewService{

    @Autowired
    private OrderRepository orderRepository;

    public OrderedBooksResponseDto getOrderedBookDetailsByOrderId(Long orderId){
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        OrderedBooksResponseDto orderedBooksResponseDto=new OrderedBooksResponseDto();
        if(optionalOrder.isPresent()){
            orderedBooksResponseDto.setOrderAmount(optionalOrder.get().getAmount());
            List<BooksDto> booksDtos=new ArrayList<>();
            for(CartItems cartItems:optionalOrder.get().getCartItems()){
                BooksDto booksDto=new BooksDto();
                booksDto.setId(cartItems.getBooks().getId());
                booksDto.setName(cartItems.getBooks().getName());
                booksDto.setPrice(cartItems.getPrice());
                booksDto.setQuantity(cartItems.getQuantity());
                booksDto.setByteImg(cartItems.getBooks().getImg());
                booksDtos.add(booksDto);
            }
            orderedBooksResponseDto.setBooksDtos(booksDtos);
        }
        return orderedBooksResponseDto;
    }
}

