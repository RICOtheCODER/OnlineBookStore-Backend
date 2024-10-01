package com.eBookStore.OnlineBookStoreProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    // You can add other fields as needed
    private Long userId;
    private String role;
}
