package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.model.Books;

import java.util.List;
import java.util.stream.Collectors;

public interface CustomerBookService {

    public List<BooksDto> getAllBooks();

    public List<BooksDto> getAllBooksByName(String name);

}
