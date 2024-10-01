package com.eBookStore.OnlineBookStoreProject.service;


import com.eBookStore.OnlineBookStoreProject.model.Coupon;
import com.eBookStore.OnlineBookStoreProject.repository.CouponRepository;
import jakarta.xml.bind.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpls implements AdminCouponService {
    @Autowired
    CouponRepository couponRepository;

    public Coupon createCoupon(Coupon coupon)  {
        if(couponRepository.existsByCode(coupon.getCode())){
            try {
                throw new ValidationException("Coupon already exists");
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons(){
        return couponRepository.findAll();
    }
}
