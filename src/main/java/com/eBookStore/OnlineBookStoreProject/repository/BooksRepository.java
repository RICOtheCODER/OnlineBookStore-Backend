package com.eBookStore.OnlineBookStoreProject.repository;


import com.eBookStore.OnlineBookStoreProject.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {

    List<Books> findAllByNameContaining(String title);
}
