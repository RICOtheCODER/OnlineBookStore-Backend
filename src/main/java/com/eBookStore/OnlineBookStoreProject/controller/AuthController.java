package com.eBookStore.OnlineBookStoreProject.controller;

import com.eBookStore.OnlineBookStoreProject.dto.AuthenticationRequest;
import com.eBookStore.OnlineBookStoreProject.dto.AuthenticationResponse;
import com.eBookStore.OnlineBookStoreProject.dto.SignupRequest;
import com.eBookStore.OnlineBookStoreProject.dto.UserDto;
import com.eBookStore.OnlineBookStoreProject.model.User;
import com.eBookStore.OnlineBookStoreProject.repository.UserRepository;
import com.eBookStore.OnlineBookStoreProject.service.AuthService;
import com.eBookStore.OnlineBookStoreProject.service.UserDetailsServiceImpl;
import com.eBookStore.OnlineBookStoreProject.utils.JwtUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;


    private final UserDetailsService userDetailsService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserDetailsServiceImpl userDetailsServiceimpl;

    @Autowired
    private final JwtUtils jwtUtils;

    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";

    @Autowired
    private final AuthService authService;
    Logger logger=LoggerFactory.getLogger(AuthController.class);

//    @PostMapping("/authenticate")
//    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
//                                          HttpServletResponse response) throws IOException, JSONException {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
//                    authenticationRequest.getPassword()));
//        }catch (BadCredentialsException e){
//            throw new BadCredentialsException("Incorrect Username or Password");
//        }
//        final UserDetails userDetails=userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
//        Optional<User> optionalUser=userRepository.findFirstByEmail(userDetails.getUsername());
//        final String jwt=jwtUtils.generateToken(userDetails.getUsername());
//        System.out.println(jwt);
//
//        if(optionalUser.isPresent()){
//            response.getWriter().write(new JSONObject().put("userId",optionalUser.get().getId())
//                    .put("role",optionalUser.get().getRole()).toString());
//        }
//        response.addHeader("Access-control-Expose-Headers","Authorization");
//        response.addHeader("Access-control-Allow-Headers","Authorization, X-PINGOTHER, Origin, "+
//                "X-Requested-With, Content-Type, Accept, X-Custom-Header");
//        response.addHeader(HEADER_STRING,TOKEN_PREFIX+jwt);
//
//    }

    @PostMapping("/authenticate")



    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),

                    authenticationRequest.getPassword()));

        } catch (BadCredentialsException e) {
              logger.error("UserName is present buddy");
          //   ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
            throw new BadCredentialsException(e.getLocalizedMessage());


        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        logger.error("USerDetails"+userDetails);
        String jwt = jwtUtils.generateToken(userDetails.getUsername());
        logger.error("JWt is"+jwt);


        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(jwt);
        response.setUserId(userDetailsServiceimpl.loadUserId(authenticationRequest.getUsername()));
        response.setRole(userDetailsServiceimpl.role(authenticationRequest.getUsername()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Access-Control-Expose-Headers", "Authorization");
        headers.add("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-Header");
        headers.add(HEADER_STRING,
                TOKEN_PREFIX + jwt);

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User Already Exists", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDto=authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
