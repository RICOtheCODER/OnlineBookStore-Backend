package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.model.Coupon;
import jakarta.xml.bind.ValidationException;

import java.util.List;

public interface AdminCouponService {
 Coupon createCoupon(Coupon coupon);

 public List<Coupon> getAllCoupons();
}
