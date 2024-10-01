package com.eBookStore.OnlineBookStoreProject.model;

import com.eBookStore.OnlineBookStoreProject.dto.BooksDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "Books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long price;

    @Lob
    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public BooksDto getDto(){
        BooksDto booksDto=new BooksDto();
        booksDto.setId(id);
        booksDto.setName(name);
        booksDto.setPrice(price);
        booksDto.setDescription(description);
        booksDto.setByteImg(img);
        booksDto.setCategoryId(category.getId());
        booksDto.setCategoryName(category.getName());
        return booksDto;
    }
}
