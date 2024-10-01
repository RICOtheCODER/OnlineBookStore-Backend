package com.eBookStore.OnlineBookStoreProject.controller;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.dto.FaqDto;
import com.eBookStore.OnlineBookStoreProject.service.AdminBookService;
import com.eBookStore.OnlineBookStoreProject.service.AdminFaqService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class adminBookController {

    Logger logger= LoggerFactory.getLogger(adminCategoryController.class);
    @Autowired
    private AdminBookService adminBookService;

    @Autowired
    private AdminFaqService adminFaqService;


    @PostMapping("/book")
    public ResponseEntity<BooksDto> addProduct(@ModelAttribute BooksDto booksDto) throws IOException {
        BooksDto booksDto1=adminBookService.addBooks(booksDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(booksDto1);
    }

    @GetMapping("/books")
    public ResponseEntity<List<BooksDto>> getAllBooks(){
        List<BooksDto> booksDtos=adminBookService.getAllBooks();
        return ResponseEntity.ok(booksDtos);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<BooksDto>> getAllBookByName(@PathVariable String name){
        logger.error(name);
        List<BooksDto> booksDtos=adminBookService.getAllBooksByName(name);
        logger.error(booksDtos.toString());
        return ResponseEntity.ok(booksDtos);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long bookId){
        logger.error(bookId.toString());
        boolean deleted =adminBookService.deleteBook(bookId);
        if(deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/faq/{bookId}")
    public ResponseEntity<FaqDto> postFaq(@PathVariable Long bookId,@RequestBody FaqDto faqDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(adminFaqService.postFaq(bookId,faqDto));
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BooksDto> getBooksById(@PathVariable Long bookId){
        BooksDto booksDto=adminBookService.getBookById(bookId);
        if(booksDto!=null){
            return ResponseEntity.ok(booksDto);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<BooksDto> updateBooks(@PathVariable Long bookId,@ModelAttribute BooksDto booksDto) throws IOException {
        BooksDto updatedBooks=adminBookService.updateBooks(bookId, booksDto);
        if(updatedBooks!=null){
            return ResponseEntity.ok(updatedBooks);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
