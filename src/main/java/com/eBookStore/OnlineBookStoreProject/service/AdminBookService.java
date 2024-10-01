package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;

import java.io.IOException;
import java.util.List;

public interface AdminBookService {

    BooksDto addBooks(BooksDto booksDto) throws IOException;
    public List<BooksDto> getAllBooks();

    public List<BooksDto> getAllBooksByName(String name);

    public boolean deleteBook(Long id);

    BooksDto getBookById(Long bookId);

    BooksDto updateBooks(Long bookId,BooksDto booksDto) throws IOException;
}
