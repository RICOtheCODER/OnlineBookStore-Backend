package com.eBookStore.OnlineBookStoreProject.repository;

import com.eBookStore.OnlineBookStoreProject.model.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {
//    Optional<CartItems> findByBooksIdAndOrderIdAndUserId(Long bookId,Long orderId,Long userId);
Optional<CartItems> findByOrderIdAndUserId(Long orderId,Long userId);

CartItems findByUserId(Long userId);

}
