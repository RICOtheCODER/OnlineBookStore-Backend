package com.eBookStore.OnlineBookStoreProject.dto;

import com.eBookStore.OnlineBookStoreProject.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private long id;
    private String email;
    private String name;
    private UserRole userRole;
}
