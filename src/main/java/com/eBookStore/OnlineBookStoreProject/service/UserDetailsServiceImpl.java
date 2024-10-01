package com.eBookStore.OnlineBookStoreProject.service;

import com.eBookStore.OnlineBookStoreProject.config.RoleGrantedAuthority;
import com.eBookStore.OnlineBookStoreProject.enums.UserRole;
import com.eBookStore.OnlineBookStoreProject.model.User;
import com.eBookStore.OnlineBookStoreProject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    Logger logger= LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String email=userRepository.findEmailByUsername(username);
        logger.error(email);
        Optional<User> optionalUser=userRepository.findFirstByEmail(username);
        Optional<User> optionalUser1=userRepository.findFirstByEmail(email);
        List<GrantedAuthority> authorities=new ArrayList<>();
        if(optionalUser.isEmpty() && optionalUser1.isPresent()){
            if(optionalUser1.get().getRole().equals(UserRole.ADMIN)){
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
          }
            return new org.springframework.security.core.userdetails.User(optionalUser1.get().getName(),optionalUser1.get().getPassword(),new ArrayList<>());
        }
        //username=usernamepassed as argument
        //findFirstByEmail: This method name implies that you want to find the first User entity based on the provided email.
        logger.error(String.valueOf(userRepository.findFirstByEmail(username)));
        if (optionalUser.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(optionalUser.get().getName(),optionalUser.get().getPassword(),new ArrayList<>());
    }

    public Long loadUserId(String username) {
        //add exception later
        Optional<User> optionalUser=userRepository.findFirstByEmail(username);
        Long id=optionalUser.get().getId();
        return id;
    }

    public String role(String username){
        Optional<User> optionalUser=userRepository.findFirstByEmail(username);
        UserRole role=optionalUser.get().getRole();
        return String.valueOf(role);
    }
}
