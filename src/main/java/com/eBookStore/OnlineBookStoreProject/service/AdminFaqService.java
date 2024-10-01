package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.FaqDto;

public interface AdminFaqService {

    FaqDto postFaq(Long bookId, FaqDto faqDto);
}
