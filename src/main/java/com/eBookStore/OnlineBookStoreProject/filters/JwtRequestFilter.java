package com.eBookStore.OnlineBookStoreProject.filters;

import com.eBookStore.OnlineBookStoreProject.config.RoleGrantedAuthority;
import com.eBookStore.OnlineBookStoreProject.service.UserDetailsServiceImpl;
import com.eBookStore.OnlineBookStoreProject.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    Logger logger= LoggerFactory.getLogger(JwtRequestFilter.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String authHeader=request.getHeader("Authorization");
         String token=null;
         String username=null;
         if(authHeader!=null && authHeader.startsWith("Bearer ")){
             token=authHeader.substring(7);
             logger.error("Token "+  token);
             username=jwtUtils.extractUsername(token);
             //this finds user
             logger.error("Username "+username);
             //can be extracted here to find user email from username
         }
      //   logger.error("SEcurityCOntextHolder"+SecurityContextHolder.getContext().getAuthentication().toString());
         if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
             List<GrantedAuthority> authorities=new ArrayList<>();
             UserDetails userDetails=userDetailsService.loadUserByUsername(username);
             if(userDetails.getUsername().equals("admin")){
                 authorities.add(new RoleGrantedAuthority("ROLE_ADMIN"));
             }

             System.out.println("UserDetails=   "+userDetails.toString());
             boolean b= jwtUtils.validateToken(token,userDetails);
             logger.error(String.valueOf(b));
             if(jwtUtils.validateToken(token,userDetails)){
                 UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                 //issue at validate token
                 authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 logger.error("Passsed");
                 SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                 logger.error("Passed too"+SecurityContextHolder.getContext());

             }
         }
         filterChain.doFilter(request,response);
    }
}
