package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.model.Books;
import com.eBookStore.OnlineBookStoreProject.repository.BooksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerBookServiceImpls implements CustomerBookService {

    @Autowired
    BooksRepository booksRepository;

    public List<BooksDto> getAllBooks(){
        List<Books> books=booksRepository.findAll();
        return books.stream().map(Books::getDto).collect(Collectors.toList());
    }

    public List<BooksDto> getAllBooksByName(String name){
        List<Books> books=booksRepository.findAllByNameContaining(name);
        return books.stream().map(Books::getDto).collect(Collectors.toList());
    }
}
