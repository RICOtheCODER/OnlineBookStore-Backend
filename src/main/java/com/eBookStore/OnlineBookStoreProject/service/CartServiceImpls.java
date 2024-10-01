package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.AddBookInCartDto;
import com.eBookStore.OnlineBookStoreProject.dto.CartItemsDto;
import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.dto.PlaceOrderDto;
import com.eBookStore.OnlineBookStoreProject.enums.OrderStatus;
import com.eBookStore.OnlineBookStoreProject.exceptions.ValidationException;
import com.eBookStore.OnlineBookStoreProject.model.*;
import com.eBookStore.OnlineBookStoreProject.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImpls implements CartService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CouponRepository couponRepository;
    Logger logger= LoggerFactory.getLogger(CartServiceImpls.class);

    @PersistenceContext
    private EntityManager entityManager;


    public ResponseEntity<?> addBookToCart(AddBookInCartDto addBookInCartDto){
        //logger.error(orderRepository.findByUserIdAndOrderStatus(addBookInCartDto.getUserId(), OrderStatus.Pending).toString());

        logger.error("User ID"+addBookInCartDto.getUserId().toString());
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addBookInCartDto.getUserId(), OrderStatus.Pending);
        if(activeOrder==null){
            //if null make cart empty
            activeOrder=orderRepository.findByUserId(addBookInCartDto.getUserId());
        }
        logger.error("ACtive order"+activeOrder.toString());
//        Optional<CartItems> optionalCartItems=cartItemRepository.findByBooksIdAndOrderIdAndUserId(addBookInCartDto.getBookId(),activeOrder.getId(),
//                addBookInCartDto.getUserId());
        Optional<CartItems> optionalCartItems=cartItemRepository.findByOrderIdAndUserId(activeOrder.getId(),
                addBookInCartDto.getUserId());

        logger.error("OptionalCartItems"+optionalCartItems.toString());
        if(optionalCartItems.isPresent()){
           // return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            CartItems existingCartItem = optionalCartItems.get();
            logger.info("ExistingCartItem"+existingCartItem.toString());
            existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
            existingCartItem.setBooks(booksRepository.findById(addBookInCartDto.getBookId()).get()); //2
            existingCartItem.setUser(userRepository.findById(addBookInCartDto.getUserId()).get()); //2
            //sus -->
            existingCartItem.setOrder(activeOrder); //2
            existingCartItem.setPrice(booksRepository.findById(addBookInCartDto.getBookId()).get().getPrice());
            logger.info("After sus existingCart Item"+existingCartItem.toString());
            CartItems updatedCartItem = cartItemRepository.save(existingCartItem);

            // Update order total amount and amount here
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + existingCartItem.getPrice());
            activeOrder.setAmount(activeOrder.getAmount() + existingCartItem.getPrice());

            orderRepository.save(activeOrder);
            System.out.println(updatedCartItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItem);
        }else {
            Optional<Books> optionalBooks=booksRepository.findById(addBookInCartDto.getBookId());
            Optional<User> optionalUser=userRepository.findById(addBookInCartDto.getUserId());

            if(optionalBooks.isPresent() && optionalUser.isPresent()){
//                CartItems cartItems=new CartItems();
//                cartItems.setBooks(optionalBooks.get());
//                cartItems.setPrice(optionalBooks.get().getPrice());
//                cartItems.setQuantity(1L);
//                cartItems.setUser(optionalUser.get());
//                cartItems.setOrder(activeOrder);
                CartItems cartItems=new CartItems();
                cartItems.setBooks(optionalBooks.get());
                cartItems.setPrice(optionalBooks.get().getPrice());
                cartItems.setQuantity(1L);
                cartItems.setUser(optionalUser.get());
                cartItems.setOrder(activeOrder);
                logger.info(cartItems.toString());
                CartItems updatedCart = cartItemRepository.save(cartItems);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount()+cartItems.getPrice());
                activeOrder.setAmount(activeOrder.getAmount()+cartItems.getPrice());
                activeOrder.getCartItems().add(cartItems);
                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(cartItems);
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Or Book not found");
            }
        }
    }

    public OrderDto getCartByUserId(Long userId){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        if(activeOrder==null){
            activeOrder=orderRepository.findByUserId(userId);
        }
        List<CartItemsDto> cartItemsDtoList=activeOrder.getCartItems().stream().map(CartItems::getCartDto)
                .collect(Collectors.toList());
        OrderDto orderDto=new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if(activeOrder.getCoupon() !=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;

    }

    public OrderDto applyCoupon(Long userId,String code){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(userId,OrderStatus.Pending);
        if(activeOrder==null){
            activeOrder=orderRepository.findByUserId(userId);
        }
        Coupon coupon=couponRepository.findByCode(code).orElseThrow(()->new ValidationException("Coupon not found"));

        if(couponIsExpired(coupon)){
            throw new ValidationException("coupon has expired");
        }
        double discountAmount=((coupon.getDiscount()/100.0)*activeOrder.getTotalAmount());
        double netAmount=activeOrder.getTotalAmount()-discountAmount;

        activeOrder.setAmount((long) netAmount);
        activeOrder.setDiscount((long)discountAmount);
        activeOrder.setCoupon(coupon);

        orderRepository.save(activeOrder);
        return  activeOrder.getOrderDto();
    }

    private boolean couponIsExpired(Coupon coupon){
        Date currentDate=new Date();
        Date expirationDate=coupon.getExpirationDate();

        return expirationDate!=null && currentDate.after(expirationDate);
    }

    public OrderDto increaseBookQuantity(AddBookInCartDto addBookInCartDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addBookInCartDto.getUserId(),OrderStatus.Pending);
        if(activeOrder==null){
            activeOrder=orderRepository.findByUserId(addBookInCartDto.getUserId());
        }
        logger.error(activeOrder.toString());
        Optional<Books> optionalBooks=booksRepository.findById(addBookInCartDto.getBookId());
        logger.error(optionalBooks.toString());
        Optional<CartItems> optionalCartItems=cartItemRepository.findByOrderIdAndUserId(activeOrder.getId(),addBookInCartDto.getUserId());
        logger.error("optionalCartItemsInc"+optionalCartItems.toString());
        //sus

        if(optionalBooks.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Books books=optionalBooks.get();

            activeOrder.setAmount(activeOrder.getAmount()+ books.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount()+books.getPrice());

            cartItems.setQuantity(cartItems.getQuantity()+1);
            if(activeOrder.getCoupon()!=null){
                double discountAmount=((activeOrder.getCoupon().getDiscount()/100.0)*activeOrder.getTotalAmount());
                double netAmount=activeOrder.getTotalAmount()-discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }
    public OrderDto decreaseBookQuantity(AddBookInCartDto addBookInCartDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(addBookInCartDto.getUserId(),OrderStatus.Pending);
        if(activeOrder==null){
            activeOrder=orderRepository.findByUserId(addBookInCartDto.getUserId());
        }
        logger.error(activeOrder.toString());
        Optional<Books> optionalBooks=booksRepository.findById(addBookInCartDto.getBookId());
        logger.error(optionalBooks.toString());
        Optional<CartItems> optionalCartItems=cartItemRepository.findByOrderIdAndUserId(activeOrder.getId(),addBookInCartDto.getUserId());
        logger.error("optionalCartItemsInc"+optionalCartItems.toString());
        //sus

        if(optionalBooks.isPresent() && optionalCartItems.isPresent()){
            CartItems cartItems=optionalCartItems.get();
            Books books=optionalBooks.get();

            activeOrder.setAmount(activeOrder.getAmount() - books.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - books.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);
            if(activeOrder.getCoupon()!=null){
                double discountAmount=((activeOrder.getCoupon().getDiscount()/100.0)*activeOrder.getTotalAmount());
                double netAmount=activeOrder.getTotalAmount()-discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long)discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder=orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(),OrderStatus.Pending);
        if(activeOrder==null){
            activeOrder=orderRepository.findByUserId(placeOrderDto.getUserId());
        }
        Optional<User> optionalUser=userRepository.findById(placeOrderDto.getUserId());
        if(optionalUser.isPresent()){
            logger.error("optionalFromPlaceOrder"+optionalUser.toString());
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setDate(new Date());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(UUID.randomUUID());

            orderRepository.save(activeOrder);

           //Order order=orderRepository.findByUserId(placeOrderDto.getUserId());
            Order order=new Order();
            order.setAmount(0L);
            order.setTotalAmount(0L);
            order.setDiscount(0L);
            order.setCoupon(null);//didnt work
            order.setUser(optionalUser.get());
            order.setOrderStatus(OrderStatus.Pending);
            orderRepository.save(order);

            //sus
          //  CartItems cartItems=cartItemRepository.findByUserId(placeOrderDto.getUserId());
//            cartItems.setBooks(null);
//            cartItems.setQuantity(0L);
//            cartItemRepository.save(cartItems);



            return activeOrder.getOrderDto();

        }
        return null;
    }
  public List<OrderDto> getMyPlacedOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId,List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered))
                .stream().map(Order::getOrderDto).collect(Collectors.toList());
  }
}
