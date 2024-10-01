package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.dto.SignupRequest;
import com.eBookStore.OnlineBookStoreProject.dto.UserDto;

public interface AuthService  {

    UserDto createUser(SignupRequest signupRequest);

    public Boolean hasUserWithEmail(String email);
}
