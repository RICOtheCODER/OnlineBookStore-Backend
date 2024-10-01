package com.eBookStore.OnlineBookStoreProject.service;


import com.eBookStore.OnlineBookStoreProject.dto.FaqDto;
import com.eBookStore.OnlineBookStoreProject.model.Books;
import com.eBookStore.OnlineBookStoreProject.model.Faq;
import com.eBookStore.OnlineBookStoreProject.repository.BooksRepository;
import com.eBookStore.OnlineBookStoreProject.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminFaqServiceImpls implements AdminFaqService {

    @Autowired
    private FaqRepository faqRepository;

    @Autowired
    private BooksRepository booksRepository;

    public FaqDto postFaq(Long bookId,FaqDto faqDto){
        Optional<Books> optionalBooks=booksRepository.findById(bookId);
        if(optionalBooks.isPresent()){
            Faq faq=new Faq();
            faq.setQuestion(faqDto.getQuestion());
            faq.setAnswer(faqDto.getAnswer());
            faq.setBooks(optionalBooks.get());

            return faqRepository.save(faq).getFaqDto();
        }
        return null;
    }


}
