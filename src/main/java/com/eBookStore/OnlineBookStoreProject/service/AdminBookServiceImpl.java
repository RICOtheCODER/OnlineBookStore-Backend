package com.eBookStore.OnlineBookStoreProject.service;


import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.eBookStore.OnlineBookStoreProject.model.Books;
import com.eBookStore.OnlineBookStoreProject.model.Category;
import com.eBookStore.OnlineBookStoreProject.repository.BooksRepository;
import com.eBookStore.OnlineBookStoreProject.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public BooksDto addBooks(BooksDto booksDto) throws IOException {
        Books books=new Books();
        books.setName(booksDto.getName());
        books.setDescription(booksDto.getDescription());
        books.setPrice(booksDto.getPrice());
        books.setImg(booksDto.getImg().getBytes());
        Category category=categoryRepository.findById(booksDto.getCategoryId()).orElseThrow();
        books.setCategory(category);
        return booksRepository.save(books).getDto();
    }

    public List<BooksDto> getAllBooks(){
        List<Books> books=booksRepository.findAll();
        return books.stream().map(Books::getDto).collect(Collectors.toList());
    }

    public List<BooksDto> getAllBooksByName(String name){
        List<Books> books=booksRepository.findAllByNameContaining(name);
        return books.stream().map(Books::getDto).collect(Collectors.toList());
    }

    public boolean deleteBook(Long id){
        Optional<Books> optionalBooks=booksRepository.findById(id);
        if(optionalBooks.isPresent()){
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public BooksDto getBookById(Long bookId){
        Optional<Books> optionalBooks=booksRepository.findById(bookId);
        if(optionalBooks.isPresent()){
            return optionalBooks.get().getDto();
        }
        else {
            return null;
        }
    }

    public BooksDto updateBooks(Long bookId,BooksDto booksDto) throws IOException {
        Optional<Books> optionalBooks=booksRepository.findById(bookId);
        Optional<Category> optionalCategory=categoryRepository.findById(booksDto.getCategoryId());
        if(optionalBooks.isPresent() && optionalCategory.isPresent()){
            Books books=optionalBooks.get();

            books.setName(booksDto.getName());
            books.setPrice(booksDto.getPrice());
            books.setDescription(booksDto.getDescription());
            books.setCategory(optionalCategory.get());
            if(booksDto.getImg()!=null){
                books.setImg(booksDto.getImg().getBytes());
            }
            return booksRepository.save(books).getDto();
        }
        else {
            return null;
        }
    }
}
