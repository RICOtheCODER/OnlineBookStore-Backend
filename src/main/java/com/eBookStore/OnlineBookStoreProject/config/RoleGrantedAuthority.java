package com.eBookStore.OnlineBookStoreProject.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


public class RoleGrantedAuthority   implements GrantedAuthority {

    private String authority;

    public RoleGrantedAuthority(String authority) {
        this.authority = authority;
    }
    @Override
    public String getAuthority() {
        return authority;
    }
}
