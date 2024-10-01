package com.eBookStore.OnlineBookStoreProject.repository;

import com.eBookStore.OnlineBookStoreProject.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {
    boolean existsByCode(String code);

    Optional<Coupon> findByCode(String code);
}
