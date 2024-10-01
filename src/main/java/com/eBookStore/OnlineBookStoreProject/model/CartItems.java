package com.eBookStore.OnlineBookStoreProject.model;

import com.eBookStore.OnlineBookStoreProject.dto.CartItemsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItems implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "book_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Books books;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    public CartItemsDto getCartDto(){
        CartItemsDto cartItemsDto=new CartItemsDto();
        cartItemsDto.setId(id);
        cartItemsDto.setPrice(price);
        cartItemsDto.setBookId(books.getId());
        cartItemsDto.setQuantity(quantity);
        cartItemsDto.setUserId(user.getId());
        cartItemsDto.setBookName(books.getName());
        cartItemsDto.setReturnedImg(books.getImg());

        return cartItemsDto;
    }

    @Override
    public String toString() {
        return "CartItems{" +
                "id=" + id +
                ", price=" + price +
                ", quantity=" + quantity +
                // Avoid including books, user, and order to prevent recursion
                // ", books=" + books +
                // ", user=" + user +
                // ", order=" + order +
                '}';
    }
}
