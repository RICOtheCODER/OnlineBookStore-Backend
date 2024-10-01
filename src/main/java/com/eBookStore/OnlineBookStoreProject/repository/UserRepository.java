package com.eBookStore.OnlineBookStoreProject.repository;

import com.eBookStore.OnlineBookStoreProject.enums.UserRole;
import com.eBookStore.OnlineBookStoreProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


  Optional<User> findFirstByEmail(String email);

  User findByRole(UserRole userRole);

  @Query("SELECT u.email FROM User u WHERE u.name = :username")
  String findEmailByUsername(@Param("username") String username);

}
