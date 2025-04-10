package com.codezayneb.ecom.controller;


import com.codezayneb.ecom.Utils.JwtUtil;
import com.codezayneb.ecom.dto.AuthentificationRequest;
import com.codezayneb.ecom.dto.SignupRequest;
import com.codezayneb.ecom.dto.UserDto;
import com.codezayneb.ecom.entity.User;
import com.codezayneb.ecom.repository.UserRepository;
import com.codezayneb.ecom.services.auth.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    private final AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthentificationRequest authenticationRequest) throws JSONException {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Incorrect username or password."));
        }

        // Récupération des détails de l'utilisateur
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            JSONObject responseJson = new JSONObject();
            responseJson.put("token", jwt);
            responseJson.put("userId", user.getId());
            responseJson.put("role", user.getRole());

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwt);
            headers.set("Access-Control-Expose-Headers", "Authorization");
            headers.set("Access-Control-Allow-Headers",
                    "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header");

            return ResponseEntity.ok().headers(headers).body(responseJson.toString());
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("error", "User not found."));
    }

    @PostMapping("/sign-up")
    @Transactional

    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {

        if(authService.hasUserWithEmail (signupRequest.getEmail())){
        return new ResponseEntity<> ("User already exists", HttpStatus.NOT_ACCEPTABLE);
          }
        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

        }
        }