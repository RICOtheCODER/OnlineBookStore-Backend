package com.eBookStore.OnlineBookStoreProject.controller;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.dto.OrderDto;
import com.eBookStore.OnlineBookStoreProject.service.CustomerBookService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerBookController {
    @Autowired
    private CustomerBookService customerBookService;

    Logger logger= LoggerFactory.getLogger(CustomerBookController.class);

    @GetMapping("/books")
    public ResponseEntity<List<BooksDto>> getAllBooks(){
        List<BooksDto> booksDtos=customerBookService.getAllBooks();
        return ResponseEntity.ok(booksDtos);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<List<BooksDto>> getAllBookByName(@PathVariable String name){
        List<BooksDto> booksDtos=customerBookService.getAllBooksByName(name);
        return ResponseEntity.ok(booksDtos);
    }

}
