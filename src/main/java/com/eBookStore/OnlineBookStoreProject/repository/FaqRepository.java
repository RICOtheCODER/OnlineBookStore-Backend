package com.eBookStore.OnlineBookStoreProject.repository;

import com.eBookStore.OnlineBookStoreProject.model.Faq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaqRepository extends JpaRepository<Faq,Long> {
}
