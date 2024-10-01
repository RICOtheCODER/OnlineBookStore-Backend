package com.eBookStore.OnlineBookStoreProject.model;

import com.eBookStore.OnlineBookStoreProject.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String password;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;
}
